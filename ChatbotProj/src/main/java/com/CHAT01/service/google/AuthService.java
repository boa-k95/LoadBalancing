package com.CHAT01.service.google;

import com.intesasanpaolo.bear.service.BaseService;
import com.CHAT01.connector.dlp.google.token.AccessTokenRestConnector;
import com.CHAT01.connector.dlp.google.token.dto.AccessTokenRequest;
import com.CHAT01.connector.dlp.google.token.dto.AccessTokenResponse;
import com.CHAT01.connector.dlp.google.token.model.AccessToken;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import com.CHAT01.connector.dlp.google.token.transformer.AccessTokenRestRequestTransformer;
import com.CHAT01.connector.dlp.google.token.transformer.AccessTokenRestResponseTransformer;
import com.CHAT01.service.factory.AuthServiceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;

import static util.DateTimeUtil.ROME_ZONE_ID;
@Configuration
@EnableScheduling
@Service
public class AuthService extends BaseService {

    public static final int MAX_RETRY_NUMBER = 5;

    @Autowired private AccessTokenRestResponseTransformer responseTransformer;
    @Autowired private AccessTokenRestRequestTransformer requestTransformer;
    @Autowired private AccessTokenRestConnector accessTokenRestConnector;


    /*------- authorization com.CHAT01.factory--------*/
    @Autowired AuthServiceFactory authServiceFactory;

     @Value("${google.integration.amount")
      private  Integer amount;

     @Value("${google.integration.unit")
     private String unit;

    private Map<ChannelEnum, AccessToken> accessMapToken;

    private Duration accessTokenRefreshTolerance = null;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy - HH:mm:ss z");
    private boolean applicationReady = false;

     @EventListener(ApplicationReadyEvent.class)
    public void initializeAccessToken(ApplicationReadyEvent event) { 
         Integer chronoAmount;
         ChronoUnit chronoUnit;

         /* -- Genero una struttura dati di Token -- */
         accessMapToken = authServiceFactory.createAccessTokenMap();

         try {
             chronoUnit = ChronoUnit.valueOf(unit);
             chronoAmount = amount;
         } catch (Exception e) {
             logger.warn("Configurazione google.integration.unit non riconosciuta come un valore valido di ChronoUnit, abilitazione configurazioni di default");
             chronoUnit = ChronoUnit.MINUTES;
             chronoAmount = 10;
         }

         try {
             accessTokenRefreshTolerance = Duration.of(chronoAmount, chronoUnit);
             logger.info("Prima inizializzazione dei Google Access Token corso...");

             /* CONTROLLO AGGIUNTO PER EVITARE LA PRIMA CHIAMATA IN FASE DI TESTING */
             if (!Arrays.asList(event.getApplicationContext().getEnvironment().getActiveProfiles()).contains("unittests")) {
                 for (ChannelEnum channel : accessMapToken.keySet()){
                     try {
                         logger.info("Inizializzazione del Google Access Token del canale: {}", channel);
                         refreshAccessToken(channel);
                     }catch (Exception e){
                         logger.warn("Errore durante l'inizializzazione del Google Access Token del canale: {}", channel);
                     }
                 }
             }
             applicationReady = true;
             logger.info("Prima inizializzazione dei Google Access Token completata");
         } catch (Exception e) {
             logger.error("Errore durante la prima inizializzazione dei Google Access Token", e);
         }
     }

    @Scheduled(cron = "${google.integration.cron}")
    public void autorefreshAccessToken() {
        logger.info("Tentativo di refresh Access Token OAuth Google [applicationReady = {}]", applicationReady);
        if (applicationReady) {
            Instant now = Instant.now();
            /*Verifico e aggiorno per ogni canale presente nella mappa se è scaduto o meno, se cosi fosse verrà aggiornato*/
            for (Map.Entry<ChannelEnum,AccessToken> entry : accessMapToken.entrySet()){
                ChannelEnum channel = entry.getKey();
                AccessToken token = entry.getValue();
                if (token.getTokenPreExpired() == null || !now.isBefore(token.getTokenPreExpired())){
                    logger.info("Refresh del Google Access Token del canale {} in corso...", channel);
                    try {
                        refreshAccessToken(channel);
                    }catch (Exception e){
                        logger.warn("Errore durante il refresh automatico del Google Access Token del canale: {}", channel);
                    }
                }
                else {
                    logger.info("Refresh pianificato del Google Access Token del canale {} non necessario: Access token: {}", channel, token.getToken());
                }
            }
        }
    }
    public synchronized String getAccessToken(ChannelEnum channel) {
        if (StringUtils.isBlank(accessMapToken.get(channel).getToken())) {
            /* Difficile che sia null, dato che in fase di @PostConstruct ho inizializzato subito il bean
             * facendomi sganciare un primo access token. Meglio controllarlo, in caso di errori ho un tentativo
             * in piu' prima di restituire un access token pari a NULL e far fallire una chiamata. */
            try {
                refreshAccessToken(channel);
            } catch (Exception e) {
                logger.error("Errore durante l'aggiornamento del Google Access Token del canale {}", channel);
            }
        }
        return accessMapToken.get(channel).getToken();
    }

    private synchronized boolean refreshAccessToken(ChannelEnum channel) throws Exception {
        AccessTokenRequest input = authServiceFactory.createRefreshTokenRequest(channel);
        AccessTokenResponse output;
        /* Prelevo l'access token attuale */
        AccessToken accessToken = new AccessToken();

        boolean success = false;
        for (int i = 0; i < MAX_RETRY_NUMBER && !success; i++) {
            try {
                output = accessTokenRestConnector.call(input, requestTransformer, responseTransformer);
                if (output != null && StringUtils.isNotBlank(output.getAccess_token())) {
                    accessToken.setToken(output.getAccess_token());
                    Instant now = Instant.now();

                    Long accessExpiresInSeconds = 0L;
                    if (output.getExpires_in() != null) {
                        accessExpiresInSeconds = output.getExpires_in();
                    }

                    /* Istante di scadenza Google */
                    Instant accessExpiringInstant = now.plusSeconds(accessExpiresInSeconds);
                    accessToken.setTokenExpired( accessExpiringInstant);

                    /* Formattazione della data per il logger */
                    String accessTokenExpiringDate = accessExpiringInstant.atZone(ROME_ZONE_ID).format(formatter);
                    logger.info("Nuovo Google Access Token: Canale [{}], Token [{}], Exipired: [{}]", channel, accessToken.getToken(), accessTokenExpiringDate);

                    /* Istante di scadenza anticipato di "accessTokenRefreshTollerance"*/
                    Instant accessTokenRefreshInstant = now.plusSeconds(accessExpiresInSeconds).minus(accessTokenRefreshTolerance);
                    accessToken.setTokenPreExpired(accessTokenRefreshInstant);
                    String accessTokenRefreshDate = accessTokenRefreshInstant.atZone(ROME_ZONE_ID).format(formatter);
                    logger.info("Ricalcolo del Google Access Token pianificato non prima di [{}]", accessTokenRefreshDate);

                    accessMapToken.put(channel,accessToken);
                    success = true;
                }
            } catch (Exception e) {
                logger.error("Eccezione durante il prelievo del Google Access Token del canale {}. Tentativo numero {} della serie {}", channel, (i + 1), (i + 1), e);
            }
        }
        /* Se tutto va a buon fine, l'access token viene aggiornato con uno nuovo, altrimenti:
         * 1 - Se l'attuale token è nullo, implica che gia ha fallito l'inizializzazione, e quindi siamo senza
         * token e di conseguenza lancio una exception */
        return success;
    }
     }


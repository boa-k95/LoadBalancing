package task.dto;


import com.intesasanpaolo.bear.domain.model.be4fe.Customer;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import lombok.Data;
import com.CHAT01.model.Chat;
import com.CHAT01.model.FrontEndInputParameters;
import com.CHAT01.model.ServicesCache;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;

import java.util.Map;

@Data
public class TaskInput {
    /**
     * Sessione backend della richiesta utente
     */
    private String session;
    /**
     * Risposta della detectIntent
     */
    private DetectIntentDTO detectIntentDTO;
    /**
     * Canale di provenienza della richiesta (APP,IB,..)
     */
    private ChannelEnum channel;
    /**
     * Parametri (di input, tipo e lista di Object) comunicati dal chiamante.
     */
    private FrontEndInputParameters frontEndInputParameters;

    private Customer customer;

    private Chat chat;

    /**
     * Parametri opzionali (di aoutput) da passare insieme ad un evento durante una conversazione tecnica
     */
    private Map<String, ? super Object> eventParameters;


    /**
     * Cache utile a evitare chiamate ripetute ai servizio; ad uso e consumo esclusivo del Service
     * Incluso nell'input ai task solo per comodità nel recupero del Bean Spring
     */
    private ServicesCache servicesCache;

}

package com.CHAT01.service.google.factory;

import com.CHAT01.connector.dlp.google.token.dto.AccessTokenRequest;
import com.CHAT01.connector.dlp.google.token.model.AccessToken;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthServiceFactory {
    private static final String GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.RS256;


     @Value("${connectors.bearInternalConfigurations.items.AccessTokenRestConnector.parameters.scope") protected String scope;
    @Value("${connectors.bearInternalConfigurations.items.AccessTokenRestConnector.parameters.uri")  protected String authUrl;



    /* STATIC CONFIGURATIONS  PARAMETERS FOR BOTH CHANNELS(APP & IB)*/
    //APP
    @Value("${connectors.restConfigurations.items.AccessTokenRestConnector.parameters.app.privateKeyId}") private String appPrivateKeyId;
    @Value("${connectors.restConfigurations.items.AccessTokenRestConnector.parameters.app.clientEmail}") private String appClientEmail;
    @Value("${connectors.restConfigurations.items.AccessTokenRestConnector.parameters.app.privateKey}") private String appPrivateKeyBase64;
    //IB
    @Value("${connectors.restConfigurations.items.AccessTokenRestConnector.parameters.ib.privateKey}") private String ibPrivateKeyBase64;
    @Value("${connectors.restConfigurations.items.AccessTokenRestConnector.parameters.ib.privateKeyId}") private String ibPrivateKeyId;
    @Value("${connectors.restConfigurations.items.AccessTokenRestConnector.parameters.ib.clientEmail}") private String ibClientEmail;

    public Map<ChannelEnum, AccessToken> createAccessTokenMap() {
        Map<ChannelEnum, AccessToken> accessTokenMap = new EnumMap<>(ChannelEnum.class);
        for (ChannelEnum channel : ChannelEnum.values()){
            accessTokenMap.put(channel, new AccessToken());
        }
        return accessTokenMap;
    }
    public AccessTokenRequest createRefreshTokenRequest(ChannelEnum channel) throws Exception {
        AccessTokenRequest request = new AccessTokenRequest();
        String assertion = createAssertion(channel);
        request.setGrant_type(GRANT_TYPE);
        request.setAssertion(assertion);
        return request;
    }

    private String createAssertion(ChannelEnum channel) throws Exception {
        Map<String, Object> header = new HashMap<>();
        header.put(JwsHeader.TYPE, JwsHeader.JWT_TYPE);
        header.put(JwsHeader.ALGORITHM, SIGNATURE_ALGORITHM.getValue());

        long currentTime = ZonedDateTime.now().toEpochSecond();
        long expirationTime = currentTime + 1 * ChronoUnit.HOURS.getDuration().getSeconds();

        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.AUDIENCE, authUrl);
        claims.put(Claims.ISSUED_AT, currentTime);
        claims.put(Claims.EXPIRATION, expirationTime);
        claims.put("scope", scope);

        StringBuilder pkcs8Lines = new StringBuilder();
        String line;

        /*Raccolgo qui le informazioni che variano al variare del canale prima di utilizzarle*/
        String privateKeyBase64 = null;
        switch (channel){
            case APP:{
                header.put(JwsHeader.KEY_ID, appPrivateKeyId);
                claims.put(Claims.ISSUER, appClientEmail);
                privateKeyBase64 = appPrivateKeyBase64;
                break;
            }
            case IB:
                header.put(JwsHeader.KEY_ID, ibPrivateKeyId);
                claims.put(Claims.ISSUER, ibClientEmail);
                privateKeyBase64 = ibPrivateKeyBase64;
                break;
        }

        BufferedReader rdr = new BufferedReader(new StringReader(privateKeyBase64));
        while ((line = rdr.readLine()) != null) {
            pkcs8Lines.append(line);
        }
        String pkcs8Pem = pkcs8Lines.toString();

        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");

        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(pkcs8Pem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance(SignatureAlgorithm.RS256.getFamilyName());
        PrivateKey privateKey = kf.generatePrivate(keySpec);

        String assertion = Jwts.builder().setHeader(header).setClaims(claims).signWith(SignatureAlgorithm.RS256, privateKey).compact();
        return assertion;
    }

}

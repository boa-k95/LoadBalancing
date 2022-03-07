package com.CHAT01.service.google.factory;

import com.CHAT01.model.InfoTypeEnum;
import org.springframework.stereotype.Component;
import com.CHAT01.connector.dlp.dto.ContentServiceRequest;

import java.util.LinkedList;
import java.util.List;
import static com.CHAT01.model.InfoTypeEnum.*;
@Component
public class ContentServiceFactory {

    private static final List<InfoTypeEnum> DEFAULTS_INFO_TYPES = new LinkedList<>();
 static {
     /* Nome e Cognome */
//        DEFAULTS_INFO_TYPES.add(FEMALE_NAME);
//        DEFAULTS_INFO_TYPES.add(MALE_NAME);
     DEFAULTS_INFO_TYPES.add(PERSON_NAME);
     /* Mail o PEC */
     DEFAULTS_INFO_TYPES.add(EMAIL_ADDRESS);
     /* Codice Fiscale */
     DEFAULTS_INFO_TYPES.add(ITALY_FISCAL_CODE);
     /* Passaporto */
     DEFAULTS_INFO_TYPES.add(PASSPORT);
     /* Giorno e mese di nascita */
     DEFAULTS_INFO_TYPES.add(DATE_OF_BIRTH);
     /* Indirizzi */
     DEFAULTS_INFO_TYPES.add(STREET_ADDRESS);
     /* Numeri di telefono */
     DEFAULTS_INFO_TYPES.add(PHONE_NUMBER);
     /* Indirizzo IP */
     DEFAULTS_INFO_TYPES.add(IP_ADDRESS);
     /* User ID */
     // DEFAULTS_INFO_TYPES.add(GENERIC_ID);
     /* Dati sanitari */
     DEFAULTS_INFO_TYPES.add(ICD9_CODE);
     DEFAULTS_INFO_TYPES.add(ICD10_CODE);
     DEFAULTS_INFO_TYPES.add(MEDICAL_TERM);
     /* Gruppo Etnico */
     DEFAULTS_INFO_TYPES.add(ETHNIC_GROUP);
     /* Numero di carta di credito */
     DEFAULTS_INFO_TYPES.add(CREDIT_CARD_NUMBER);
     /* Codice IBAN */
     DEFAULTS_INFO_TYPES.add(IBAN_CODE);

     /* Non dovrebbe essere necessario creare custom infoTypes */
//        DEFAULTS_INFO_TYPES.add(ITALY_TARGA);
//        DEFAULTS_INFO_TYPES.add(ITALY_CARTA_IDENTITA);
//        DEFAULTS_INFO_TYPES.add(ITALY_VAT);
 }

    public ContentServiceRequest createDLPRequest(String userText, List<InfoTypeEnum> infoTypes) {
        return new ContentServiceRequest(userText, infoTypes);
    }

    public ContentServiceRequest createDefaultDLPRequest(String userText) {
        return createDLPRequest(userText, DEFAULTS_INFO_TYPES);
    }}


package task.factory;

import com.google.api.client.util.Lists;
import com.CHAT01.model.ActiveContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;


import java.util.*;

@Component
public class TaskFactory {
   public static String DATE_PARAM_NAME ="date";
    public static String TIME_PERIOD_PARAM_NAME = "accepted_appointment_date";
    public static String PROPOSED_REGULARIZATION_DATE_PARAM_NAME="proposed_regularization_date";
    public static String PROPOSED_APPOINTMENT_DATES_PARAM_NAME= "proposed_appointment_dates";
    public static String ACCEPTED_APPOINTMENT_DATE_PARAM_NAME= "accepted_appointment_date";
    public static String PROPOSED_APPOINTMENT_TIMES_PARAM_NAME= "proposed_appointment_times";
    public static String ACCEPTED_APPOINTMENT_TIME_PARAM_NAME= "accepted_appointment_time";

   private static Logger logger = LoggerFactory.getLogger(TaskFactory.class);


    /*
     * Nei contesti, Dialogflow riporta anche la frase detta dall'utente quando viene riconosciuto un'entità
     * (es.: "TipoDocumento":{"MAV" : "MAV", "MAV.original": "mav"})
     */
    private static final String USER_UTTERANCE_PARAMETER_KEY_SUFFIX = ".original";



    /**
     * This method collect all the parameters from parameters and active contexts received from Dialogflow with the last response.
     *
     * @param dialogflowOutput the DetectIntentDTO received from Dialogflow
     * @return the Map<String, Object> containing the parameters value to be passed to services
     */
    public Map<String, ? super Object> createServiceInputParams(DetectIntentDTO dialogflowOutput, boolean exploreContexts) {
        Map<String, ? super Object> serviceParamsInput = new HashMap<>();

        /* Estrai dai parametri dell'ultima richiesta ricevuta le informazioni che l'utente potrebbe aver appena comunicato */
        if (dialogflowOutput.getParameters() != null) {
            collectParameters(dialogflowOutput.getParameters(), serviceParamsInput);
        }

        /* Estrai dai contesti attivi tutte le informazioni che l'utente potrebbe aver già comunicato,
         *  senza sovrascrivere quelle nuove */
        /* Questa esplorazione ulteriore del passato può interferire con i valori di default */
        if (exploreContexts && dialogflowOutput.getOutputContexts() != null) {
            for (ActiveContext ctx : dialogflowOutput.getOutputContexts()) {
                if (ctx != null && ctx.getParameters() != null) {
                    logger.info("Controllo del contesto '{}' per recupero di info già comunicate",
                            (ctx.getName() != null) ? ctx.getName() : "");
                    collectParameters(ctx.getParameters(), serviceParamsInput);
                }
            }
        }

        return serviceParamsInput;
    }

    /**
     * This method finds out if the type of a parameter is a simple or a compound Entity.
     *
     * @param value the value of the parameters in exam
     * @return true if the parameter has a simple type, false otherwise
     */
    private static boolean hasSimpleType(Object value) {
        return value.getClass() == String.class ||
                value.getClass() == Boolean.class ||
                value.getClass() == Integer.class ||
                value.getClass() == Double.class;
    }


    private static Object filterOriginalValues(Object value) {
        Object filteredValue;
        if (value instanceof Map) {
            filteredValue = new HashMap<String, Object>();
            for (Map.Entry<String, ? super Object> entry : ((Map<String, ? super Object>) value).entrySet()) {
                if (!entry.getKey().endsWith(USER_UTTERANCE_PARAMETER_KEY_SUFFIX)) {
                    ((Map<String, ? super Object>) filteredValue).put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            filteredValue = value;
        }
        return filteredValue;
    }

    private void collectParameters(Map<String, ? super Object> dialogflowOutputParameters, Map<String, ? super Object> serviceInputParams) {
        for (Map.Entry<String, ? super Object> param : dialogflowOutputParameters.entrySet()) {
            logger.info("Search value for parameter '{}' with value {} of {}", param.getKey(), param.getValue(), param.getValue().getClass());
            if (// Non siamo interessati alle parole esatte dell'utente
                    !param.getKey().endsWith(USER_UTTERANCE_PARAMETER_KEY_SUFFIX) &&
                            // Non vogliamo sovrascrivere valori aggiornati per i parametri
                            !serviceInputParams.containsKey(param.getKey())) {

                if (param.getValue() instanceof List) {
                    if (/* Ignoro parametri opzionali non valorizzati */
                            !((List<Object>) param.getValue()).isEmpty()) {

                    /* Se il valore del parametro è una lista di elementi di tipo non definito da Dialogflow,
                        ripeto l'esplorazione per ogni elemento */
                        List<? super Object> tempBuffer = Lists.newArrayList();
                        for (Object element : ((List<Object>) param.getValue())) {
                            if (/* Ignoro parametri opzionali non valorizzati (ee il valore è vuoto, il tipo è mappato a String) */
                                    param.getValue().getClass() != String.class ||
                                            StringUtils.isNotBlank((String) param.getValue())) {
                                if (isGoogleDefinedType(param.getKey())) {
                                    /* Il parametro ha una lista per valore, ma siamo interessati al valore nella sua interezza
                                     * (es.: il parametro 'date-period' ha valore composta da una lista contenente data di inizio e data di fine) */
                                    // Non siamo interessati alle parole esatte dell'utente
                                    Object filteredElement = filterOriginalValues(element);
                                    tempBuffer.add(filteredElement);
                                } else if (hasSimpleType(element)) {
                                    /* Se il valore del parametro è un tipo semplice, allora non devo esplorarne il contenuto, ma salvarlo */
                                    tempBuffer.add(element);
                                } else {
                                /* Altrimenti, devo esplorarne il contenuto dato che il valore che mi interessa è il suo tipo base
                                    (e non il valore come passato da Dialogflow) */
                                    tempBuffer.add(exploreEntityValue(param.getKey(), element));
                                }
                            }
                        }

                        logger.info("New Value: {}", tempBuffer);
                        /* =========================================== */
                        /* IGNORE LISTS: return always the first value */
                        /* =========================================== */
                        if (!tempBuffer.isEmpty()) {
                            if (hasToBeList(param.getKey())) {
                                serviceInputParams.put(param.getKey(), tempBuffer);
                            } else {
                                serviceInputParams.put(param.getKey(), tempBuffer.get(0));
                            }
                        }
                    }
                } else if (/* Ignoro parametri opzionali non valorizzati (se il valore è vuoto, il tipo è mappato a String) */
                        (param.getValue().getClass() != String.class ||
                                StringUtils.isNotBlank((String) param.getValue()))) {
                    if (isGoogleDefinedType(param.getKey())) {
                        /* Il parametro ha una lista per valore, ma siamo interessati al valore nella sua interezza
                         * (es.: il parametro 'date-period' ha valore composta da una lista contenente data di inizio e data di fine) */
                        // Non siamo interessati alle parole esatte dell'utente
                        Object filteredValue = filterOriginalValues(param.getValue());
                        logger.info("New Value: {}", param.getValue());
                        serviceInputParams.put(param.getKey(), filteredValue);
                    } else {
                        if (hasSimpleType(param.getValue())) {
                            logger.info("New Value: {}", param.getValue());
                            serviceInputParams.put(param.getKey(), param.getValue());
                        } else {
                            serviceInputParams.put(param.getKey(), exploreEntityValue(param.getKey(), param.getValue()));
                        }
                    }
                }
            } else {
                logger.info("Parameter {} IGNORED", param.getKey());
            }
        }
    }

    private static boolean toGetValue(String paramName) {
        return
                /* RICERCA MOVIMENTI */
                /* RicercaMovimentiDiretto */
                paramName.equalsIgnoreCase("Importo");
    }



    private static boolean isGoogleDefinedType(String type) {
        return type.equalsIgnoreCase(DATE_PARAM_NAME) ||
                type.equalsIgnoreCase(TIME_PERIOD_PARAM_NAME) ||
                type.equalsIgnoreCase(PROPOSED_REGULARIZATION_DATE_PARAM_NAME) ||
                type.equalsIgnoreCase(PROPOSED_APPOINTMENT_DATES_PARAM_NAME) ||
                type.equalsIgnoreCase(ACCEPTED_APPOINTMENT_DATE_PARAM_NAME) ||
                type.equalsIgnoreCase(PROPOSED_APPOINTMENT_TIMES_PARAM_NAME) ||
                type.equalsIgnoreCase(ACCEPTED_APPOINTMENT_TIME_PARAM_NAME);
    }
    private static boolean hasToBeList(String type) {
        return type.equalsIgnoreCase(PROPOSED_APPOINTMENT_DATES_PARAM_NAME) ||
                type.equalsIgnoreCase(PROPOSED_APPOINTMENT_TIMES_PARAM_NAME);
    }

    private static Object exploreEntityValue(String paramName, Object complexValue) {
        Object valueToReturn = "";
        boolean getValue = toGetValue(paramName);

        Object oldValue = null;
        Object value = complexValue;
        /* se param ha un valore composto, mi interessa il tipo della entity base */
        while (// Il valore non di tipo semplice...
                !hasSimpleType(value) &&
                        // ... e ha almeno un elemento
                        !Lists.newArrayList(((Map<String, Object>) value).entrySet()).isEmpty()) {
            oldValue = value;
            Map<String, Object> currentMap = ((Map<String, Object>) value);
            Set<Map.Entry<String, Object>> currentEntrySet = currentMap.entrySet();
            /* Non siamo interessati alle parole esatte dell'utente */
            currentEntrySet.removeIf(v -> v.getKey().endsWith(USER_UTTERANCE_PARAMETER_KEY_SUFFIX));
            List<Map.Entry<String, Object>> currentEntryList = Lists.newArrayList(currentEntrySet);
            if (currentEntryList.size() > 1) {
                logger.warn("Multiple values found for the parameter {}: {} -> Getting {}", paramName, currentEntryList, currentEntryList.get(0));
            }
            Map.Entry<String, Object> currentEntry = currentEntryList.get(0);

            // getValue |= toGetValue(currentEntry.getKey());

            value = currentEntry.getValue();
            logger.info("New Type: {}", value);
        }
        /* Dato che invoco il metodo solo su parametri dai valori composti, faccio almeno un passo di esplorazione e
            oldValue è sempre diverso da null */
//        assert oldValue != null;
        Set<Map.Entry<String, String>> entrySet = (oldValue != null) ? ((Map<String, String>) oldValue).entrySet() : new HashSet<>();
        entrySet.removeIf(v -> v.getKey().endsWith(USER_UTTERANCE_PARAMETER_KEY_SUFFIX));
        logger.info("Bottom reached for the {} parameter", paramName);
        if (entrySet.size() == 1) {
            /*
             * In alcuni casi, per esempio quando bisogna inviare il tipo di un documento ad un servizio per una
             * ricerca, mi interessa il valore vero ('carta di credito'), in altri per uso interno mi interessa il
             * tipo dell'entity base
             */
            Object actualEntityType = (getValue) ?
                    Lists.newArrayList(entrySet).get(0).getValue() :
                    Lists.newArrayList(entrySet).get(0).getKey();
            logger.info("New Value: {}", actualEntityType);
            if (!(actualEntityType instanceof String) || StringUtils.isNotBlank(actualEntityType.toString()))
                valueToReturn = actualEntityType;
        } else {
            logger.warn("Multiple value for the parameter: {}; return the empty value", entrySet);
        }

        return valueToReturn;
    }


}
package com.CHAT01.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.CHAT01.command.dto.DetectIntentCommandInput;
import com.CHAT01.command.dto.DetectIntentCommandOutput;
import com.CHAT01.command.dto.OutputMessage;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import com.CHAT01.exceptions.DCCICommandException;

import com.CHAT01.exceptions.InvalidDialogflowResponseException;
import com.CHAT01.exceptions.InvalidTaskResponseException;
import com.CHAT01.exceptions.ServiceException;
import model.OutputParameters;
import model.ServicesCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import service.google.AgentService;
import service.google.ContentService;
import service.google.agent.dto.DetectIntentDTO;
import task.TaskService;
import task.dto.TaskOutput;

import java.util.*;


@Component
public class DetectIntentCommand extends BaseCommand<DetectIntentCommandOutput> {

    private static final String SPLIT_STRING = "\\|&\\|";
    private static final String LINK_PLACEHOLDER_STRING = "$link";
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ContentService contentService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private TaskService taskService;


    @Autowired
    //private ChatbotService chatbotService;
    private DetectIntentCommandInput commandInput;

    private List<OutputMessage> responsesBuffer = new LinkedList<>();
    DetectIntentDTO detectIntentDTO = null;

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public DetectIntentCommandOutput doExecute() throws DCCICommandException, InvalidDialogflowResponseException {

        DetectIntentCommandOutput output = new DetectIntentCommandOutput();
        //ChannelEnum channelEnum = commandInput.getChannel();

        String sessionId = commandInput.getSessionId();
    ChannelEnum channel = commandInput.getChannel();

        /*OUTPUT*/
        String outputSessionId = output.getSessionId();

        //output.setRoomId(commandInput.getChat().getRoom());
        Optional<String> originalText = Optional.ofNullable(commandInput.getText());
        String inputText = originalText.get();

        if (StringUtils.isBlank(inputText) || StringUtils.isNotBlank(inputText)) {
            inputText = contentService.getMaskedText(inputText, channel);
        }
         try {
             detectIntentDTO = agentService.detectIntent(sessionId, inputText, channel);

         }
         catch(ServiceException ex){
             logger.error("Impossibile contattare il servizio '': " + ex.getMessage(), ex.getCause());
         }

        logger.info("Identificata l'azione >{}< all'interno della sessione >{}<", detectIntentDTO.getAction(), sessionId);

        if (detectIntentDTO == null) {
            logger.error("Risposta nulla ricevuta da Dialogflow (sessionId {}): {}", sessionId, inputText + "'");
            throw new InvalidDialogflowResponseException("Nessuna risposta ricevuta da Dialogflow.");
        }

        if (StringUtils.isAllBlank(detectIntentDTO.getAction(), detectIntentDTO.getText())) {
            logger.error("No Action received OR  text received");
            throw new InvalidDialogflowResponseException("Ricevuta risposta senza action  e testo impossibile proseguire.");

        }

        if (StringUtils.isBlank(detectIntentDTO.getAction())) {
            logger.error("No Action received");
            throw new InvalidDialogflowResponseException("Ricevuta risposta senza action proseguire.");
        }

        if (!detectIntentDTO.isAllRequiredParamsPresent()) {
            /* Dialogflow dice che mancano dei parametri ma non ha specificato del testo per richiederli (non dovrebbe succedere) */
            //logger.error("Risposta non valida ricevuta da Dialogflow: {}", detectIntentDTO);
            throw new InvalidDialogflowResponseException("Ricevuta risposta incompleta (!allRequiredParamsPresent) e senza testo da Dialogflow, impossibile proseguire.");
        }

        /* Aggiornamento di output per la prossima esecuzione del ciclo o la resituzione al com.CHAT01.controller */
        if (StringUtils.isNotBlank(detectIntentDTO.getText()) &&
                (StringUtils.isBlank(detectIntentDTO.getAction()) ||
                        /* Ignoro le action non censite che vengono ricevute accompagnate da testo
                         *  ASSUNZIONE: tutte le action non censite sono definite da Google e non necessitano di operazioni da parte nostra */
                        !beanFactory.containsBean(detectIntentDTO.getAction()) ||
                        !detectIntentDTO.isAllRequiredParamsPresent())) {
            /* Non devo invocare la action anche se l'ho ricevuta */

            if (StringUtils.isNotBlank(detectIntentDTO.getAction()) && !beanFactory.containsBean(detectIntentDTO.getAction())) {
                logger.warn("Action '{}' ignorata (allRequiredParamsPresent == {})", detectIntentDTO.getAction(), detectIntentDTO.isAllRequiredParamsPresent());
            }
            responsesBuffer.add(new OutputMessage(detectIntentDTO.getText(), null));

        }
        /*
          Esecuzione di eventuale/i task
         */
        TaskOutput taskResult = null;
        /* Cache ad uso esclusivo dei service: non viene ripetuta due volte una stessa chiamata all'interno
         *  della stessa sessione HTTP (quindi va creata e distrutta dal com.CHAT01.command e passata dal task al service) */
        ServicesCache servicesCache = new ServicesCache();

        taskResult = taskService.getExecuteTask(detectIntentDTO, commandInput,new HashMap<>(), // Non si può usare Collections.emptyMap: la mappa risultante è immutabile
                servicesCache);

         if(taskResult==null ||(!taskResult.getText().isPresent() || StringUtils.isBlank(taskResult.getText().get()) && responsesBuffer.isEmpty())){
             logger.error("Risposta non valida del task per l'action {} con input\n\tDetectIntentDTO: {}\n\tCommandInput:  {}\n\tServiceCache: {}\n\n\tResponse: {}",detectIntentDTO.getAction(), detectIntentDTO, commandInput, servicesCache, taskResult);
        throw new InvalidTaskResponseException("Errore nel recupero o nell'esecuzione di un task " + detectIntentDTO.getAction());
    }


        if (taskResult.getText() != null &&
                taskResult.getText().isPresent() &&
                StringUtils.isNotBlank(taskResult.getText().get())) {
            responsesBuffer.add(new OutputMessage(taskResult.getText().get(), taskResult.getOutputParameters()));
        }

         output.setReponses(responsesBuffer);
        logger.info("printing phrase from the taskServivce ----->{}",output.getReponses());
         return output;
        }


    //private static final String SPLIT_STRING = "\\|&\\|";
    private void publishResponseAndNotify(DetectIntentCommandOutput output, boolean startedTechnical) {
        Map<String, OutputParameters> messageToPublish = new HashMap<>();

        for (OutputMessage message : output.getReponses()) {
            logger.info("Publicazione testo: {}", output);
            boolean publishedWithSuccess = true;
            // boolean notifiedWithSuccess = true;
            int publishSuccesses = 0;
            boolean parametersAdded = false;
            String[] sections = message.getResponse().split(SPLIT_STRING);
            output.setMessageNumber(sections.length);
            /* Pubblicazione messaggi con parametri approppriati annessi
             *  ASSUNZIONE: tutte le volte che il placeholder '$link' compare in una sezione,
             *      devono essere pubblicati sempre gli stessi parametri */
            for (int i = 0; i < sections.length - 1; i++) {
                String responseSection = sections[i];
                logger.info("Publicazione sezione: \"{}\"", responseSection);
                String trimmedSection;
                boolean addParameters = false;
                if (responseSection.contains(LINK_PLACEHOLDER_STRING)) {
                    addParameters = true;
                    parametersAdded = true;
                    trimmedSection = responseSection.replace(LINK_PLACEHOLDER_STRING, "");
                } else {
                    trimmedSection = responseSection;
                }
                trimmedSection = trimmedSection.trim();
                /* Change \s to ' ', but ignores \n (part of the correct formatting of the messages):
                 *   the regular expression [ \t\n\x0B\f\r] == \s in Java (https://www.baeldung.com/java-regex-s-splus#diff),
                 *   but we must replace [\t\x0B\f\r] with a single space, and multiple space with a single space, and leave \n unchanged */
                trimmedSection = trimmedSection.replaceAll(" {2,}|[\\t\\x0B\\f\\r]", " ");
                if (trimmedSection.contains("$") || trimmedSection.contains("#")) {
                    logger.warn("Incomplete text for the user: {}", trimmedSection);
                }
                logger.info("Pubblicazione messaggio '{}'", trimmedSection);
                boolean currentSuccess = true;

                if (StringUtils.isNotBlank(trimmedSection)) {
                 /*currentSuccess = chatbotService.publishMessage(output.getRoomId(), trimmedSection,
                            (addParameters) ? message.getOutputParameters() : null);
                            }*/
                    messageToPublish.put(trimmedSection, (addParameters) ? message.getOutputParameters() : null);
                    currentSuccess = true;
                }
                if (currentSuccess) {
                    publishSuccesses++;
                }

                /* Notifiche spente */
                // notifiedWithSuccess &= ocapService.sendPushNotification(trimmedSection, input.getCustomer());
                publishedWithSuccess &= currentSuccess;
            }
        /* Pubblicazione ultimo messaggio: nel caso siano presenti dei parametri
        e non siano ancora stati inseriti, li inserisco nell'ultimo messaggio*/
            String responseSection = sections[sections.length - 1];
            logger.info("Publicazione sezione: \"{}\"", responseSection);
            String trimmedSection;
            boolean addParameters = false;
            if (responseSection.contains(LINK_PLACEHOLDER_STRING) || !parametersAdded) {
                addParameters = true;
                trimmedSection = responseSection.replace(LINK_PLACEHOLDER_STRING, "");
            } else {
                trimmedSection = responseSection;
            }
            trimmedSection = trimmedSection.trim();
            /* Change \s to ' ', but ignores \n (part of the correct formatting of the messages):
             *   the regular expression [ \t\n\x0B\f\r] == \s in Java (https://www.baeldung.com/java-regex-s-splus#diff),
             *   but we must replace [\t\x0B\f\r] with a single space, and multiple space with a single space, and leave \n unchanged */
            trimmedSection = trimmedSection.replaceAll(" {2,}|[\\t\\x0B\\f\\r]", " ");
            if (trimmedSection.contains("$") || trimmedSection.contains("#")) {
                logger.warn("Incomplete text for the user: {}", trimmedSection);
            }
            logger.info("Pubblicazione messaggio '{}'", trimmedSection);
            boolean currentSuccess = true;
            if (StringUtils.isNotBlank(trimmedSection)) {
               /* currentSuccess = chatbotService.publishMessage(output.getRoomId(), trimmedSection,
                        (addParameters) ? message.getOutputParameters() : null);
                         */

                messageToPublish.put(trimmedSection, (addParameters) ? message.getOutputParameters() : null);

            }
            if (currentSuccess) {
                publishSuccesses++;
            }
            /* Notifiche spente (per ora) */
            /* notifiedWithSuccess &= ocapService.sendPushNotification(trimmedSection, input.getCustomer()); */
            publishedWithSuccess &= currentSuccess;
            output.setAck(publishSuccesses);

            logger.info("Pubblicazione messaggio conclusa {} successo", publishedWithSuccess ? "con" : "senza");
            if (!publishedWithSuccess) {
                throw new DCCICommandException("Possibile pubblicazione parziale dei messaggi per l'utente " + ((startedTechnical) ? "(evento)" : "(testo)"), "CHATBOTERR005", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}



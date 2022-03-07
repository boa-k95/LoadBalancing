package com.CHAT01.service.google;

import com.intesasanpaolo.bear.service.BaseService;

import com.CHAT01.connector.dlp.google.agent.dto.DetectIntentRequest;
import com.CHAT01.connector.dlp.google.agent.dto.DetectIntentResponse;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import com.CHAT01.exceptions.DCCINoDialogflowResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.CHAT01.service.factory.AgentServiceFactory;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;
import com.CHAT01.service.google.connector.DetectIntentRestConnector;
import com.CHAT01.service.google.trasfomers.inputTrasformers.DetectIntentRestRquestTransformer;
import com.CHAT01.service.google.trasfomers.outputTrasformers.DetectIntentRestResponseTransformer;

@Service
public class AgentService extends BaseService {
    @Autowired private DetectIntentRestConnector connector;
    @Autowired private DetectIntentRestRquestTransformer requestTransformer;
    @Autowired private DetectIntentRestResponseTransformer responseTransformer;

    @Autowired private AgentServiceFactory factory;

    public DetectIntentDTO detectIntent(String sessionId, String message, ChannelEnum channel){
       AgentServiceFactory.DetectIntentRequestType type =AgentServiceFactory.DetectIntentRequestType.TEXT;
        DetectIntentRequest request = factory.createDetectIntentRequest(type,message,null,null);
        DetectIntentResponse response;
        DetectIntentDTO output = null;

        String detectAgentProjectId = factory.getProjectIdByChannel(channel);

        try {
            response = connector.call(request, requestTransformer, responseTransformer, detectAgentProjectId, /*sessionId,*/ channel);
        } catch (Exception e) {
            logger.error("Eccezione durante l'invocazione del servizio REST per il recupero della risposta del chatbot: {} -> {}", e.getCause(), e.getMessage());
            throw new DCCINoDialogflowResponseException("Impossibile contattare Dialogflow (con testo)",
                    "CHATBOTERR003", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response != null) {
            output = factory.createDetectIntentDTO(response);
        } else {
            logger.error("Impossibile ricevere risposte da Dialogflow per il messaggio '{}'", message);
        }

        return output;
    }

  /*  public DetectIntentDTO sendEvent(String sessionId, Event event, ChannelEnum channel, ActiveContext context) {
        AgentServiceFactory.DetectIntentRequestType type = AgentServiceFactory.DetectIntentRequestType.EVENT;
        DetectIntentRequest request = com.CHAT01.factory.createDetectIntentRequest(type, null, event, context);
        DetectIntentResponse response = null;
        DetectIntentDTO output = null;

        String detectAgentProjectId = com.CHAT01.factory.getProjectIdByChannel(channel);

        try {
            response = connector.call(request, requestTransformer, responseTransformer, detectAgentProjectId, sessionId, channel);
        } catch (Exception e) {
            logger.error("Eccezione durante l'invocazione del servizio REST per il recupero della risposta del chatbot: {} -> {}", e.getCause(), e.getMessage());
        }

        if (response != null) {
            output = com.CHAT01.factory.createDetectIntentDTO(response);
        } else {
            logger.error("Impossibile ricevere risposte da Dialogflow per l'evento '{}'", event.getEventName());
        }

        return output;
    }
*/
}



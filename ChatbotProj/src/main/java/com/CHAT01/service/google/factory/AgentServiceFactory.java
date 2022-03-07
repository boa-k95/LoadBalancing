package com.CHAT01.service.google.factory;

import com.CHAT01.connector.dlp.google.agent.dto.*;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;


import com.CHAT01.model.ActiveContext;
import com.CHAT01.model.Event;
import com.google.api.client.util.GenericData;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;

import java.util.*;

@Component
public class AgentServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(AgentServiceFactory.class);

    private static final String LANGUAGE_CODE = "it";
    private static final String TIMEZONE = "Europe/Rome";
    private static final String END_CONVERSATION_KEY = "end_conversation";

    @Value("${connectors.restConfigurations.items.DetectIntentRestConnector.parameters.appProjectId}")
    private String APP_GOOGLE_PROJECT_ID;
    @Value("${connectors.restConfigurations.items.DetectIntentRestConnector.parameters.ibProjectId}")
    private String IB_GOOGLE_PROJECT_ID;

    @Value("${google.contexts.lifespan}")
    private Integer DEFAULT_GOOGLE_CONTEXT_LIFESPAN;

    public enum DetectIntentRequestType {
        TEXT,
        EVENT;
    }

    private static final String ACTION_EVENT_SEPARATOR = ":";

    private static final String BUTTONS_SEPARATOR = "\\|";


    /**
     * Restituisce il projectId dell'agente da utilizzare
     * in relazione al canale utilizzato.
     *
     * @param channel
     * @return
     */
    public String getProjectIdByChannel(ChannelEnum channel) {
        switch (channel) {
            case APP:
                return APP_GOOGLE_PROJECT_ID;
            case IB:
                return IB_GOOGLE_PROJECT_ID;
        }
        return null;
    }
    /**
     * @param type
     * @param message
     * @param event
     * @return
     */
    public DetectIntentRequest createDetectIntentRequest(DetectIntentRequestType type, String message, Event event, ActiveContext context) {
        DetectIntentRequest request = new DetectIntentRequest();
        DetectIntentRequest.DetectIntentQueryInput input = request.new DetectIntentQueryInput();
        DetectIntentRequest.DetectIntentQueryParams params = request.new DetectIntentQueryParams();

        if (type.equals(DetectIntentRequestType.TEXT)) {
            DetectIntentRequest.DetectIntentQueryText queryText = request.new DetectIntentQueryText();
            queryText.setText(message);
            queryText.setLanguageCode(LANGUAGE_CODE);
            input.setText(queryText);
            input.setEvent(null);
        } else {
            /* type == DetectIntentRequestType.EVENT */
            DetectIntentRequest.DetectIntentQueryEvent queryEvent = request.new DetectIntentQueryEvent();
            queryEvent.setName(event.getEventName());
            queryEvent.setLanguageCode(LANGUAGE_CODE);
            if (event.getEventParameters() != null && !event.getEventParameters().isEmpty()) {
                GenericData genericData = new GenericData();
                genericData.putAll(event.getEventParameters());
                queryEvent.setParameters(genericData);
            }
            input.setEvent(queryEvent);
            input.setText(null);
        }
        if (context != null && StringUtils.isNotBlank(context.getName())) {
            /* Setting contesti attivi per passaggio date e orari proposti all'utente */
            List<DetectIntentRequest.DetectIntentQueryContext> contextList = new ArrayList<>();
            DetectIntentRequest.DetectIntentQueryContext newContext = request.new DetectIntentQueryContext();
            newContext.setName(context.getName());
            newContext.setLifespanCount(DEFAULT_GOOGLE_CONTEXT_LIFESPAN);
            Map<String, Object> contextParams = new HashMap<>(context.getParameters());
            newContext.setParameters(contextParams);
            contextList.add(newContext);
            params.setContexts(contextList);
        }

        params.setTimeZone(TIMEZONE);
        request.setQueryInput(input);
        request.setQueryParams(params);
     return request;
    }


    /**
     * @param response
     * @return
     */
    public DetectIntentDTO createDetectIntentDTO(DetectIntentResponse response) {
        DetectIntentDTO detectIntentDTO = new DetectIntentDTO();
        detectIntentDTO.setResponseId(response.getResponseId());

        DetectIntentResult queryResult = response.getQueryResult();

        if (queryResult != null) {
            detectIntentDTO.setText(queryResult.getFulfillmentText());

            /* Nel campo action di DialogFlow riceviamo una action e un event, separati dai due punti. */
            String actionAndEvent = queryResult.getAction();
            String[] actionEventArray = {};
            if (StringUtils.isNotEmpty(actionAndEvent)) {
                actionEventArray = actionAndEvent.split(ACTION_EVENT_SEPARATOR);
            }
            String action = "";
            String baseEvent = "";
            List<String> buttons = null;
            if (actionEventArray.length >= 1 && StringUtils.isNotEmpty(actionEventArray[0])) {
                /* Insieme al nome della action, nel caso di get_buttons, potrei aver ricevuto una lista di stringhe
                 * per la creazione dei bottoni separati da una pipe */
                String[] sections = actionEventArray[0].split(BUTTONS_SEPARATOR);
                action = sections[0];
                if (sections.length > 1) {
                    /* Sono presenti dei bottoni */
                    buttons = new LinkedList<>();
                    for (int i = 1; i < sections.length; i++) {
                        String button = sections[i];
                        if (StringUtils.isNotBlank(button)) {
                            buttons.add(button);
                        }
                    }
                }
            }
            if (actionEventArray.length >= 2 && StringUtils.isNotEmpty(actionEventArray[1])) {
                baseEvent = actionEventArray[1];
            }

            detectIntentDTO.setAction(action);
            detectIntentDTO.setButtons(buttons);
            detectIntentDTO.setBaseEvent(baseEvent);
            detectIntentDTO.setParameters(queryResult.getParameters());

            List<ActiveContext> activeContexts = new LinkedList<>();
            if (queryResult.getOutputContexts() != null) {
                for (ActiveContextDTO dto : queryResult.getOutputContexts()) {
                    ActiveContext activeCtx = new ActiveContext();
                    activeCtx.setName(dto.getName());
                    activeCtx.setParameters(dto.getParameters());
                    activeContexts.add(activeCtx);
                }
            }
                detectIntentDTO.setOutputContexts((activeContexts.isEmpty()) ? null : activeContexts);

            detectIntentDTO.setAllRequiredParamsPresent(queryResult.isAllRequiredParamsPresent());
            detectIntentDTO.setLanguageCode(queryResult.getLanguageCode());

            DetectIntentResultIntent intent = queryResult.getIntent();
            if (ObjectUtils.allNotNull(intent)) {
                detectIntentDTO.setIntentId(intent.getName());
                detectIntentDTO.setIntentName(intent.getDisplayName());
            }

            List<String> messages = new ArrayList<>();
            Map<String, String> customPayload = new HashMap<>();
            if (ObjectUtils.allNotNull(queryResult.getFulfillmentMessages()))
                for (DetectIntentResultMessage message : queryResult.getFulfillmentMessages()) {
                    if (ObjectUtils.allNotNull(message, message.getText())) {
                        messages.addAll(message.getText().getText());
                    }
                    if (MapUtils.isNotEmpty(message.getPayload())) {
                        customPayload = message.getPayload();
                    }
                }
            detectIntentDTO.setCustomPayload(customPayload);
            detectIntentDTO.setMessages(messages);

            if (queryResult.getIntentDetectionConfidence() != null) {
                detectIntentDTO.setDetectionConfidence(queryResult.getIntentDetectionConfidence());
            }

            Optional<Map<String, Object>> diagnosticInfo = Optional.ofNullable(queryResult.getDiagnosticInfo());
            boolean conversationClosedDiagnosticInfo = diagnosticInfo.isPresent() && (boolean) diagnosticInfo.get().getOrDefault(END_CONVERSATION_KEY, Boolean.FALSE);
            boolean conversationClosedIntentEndInter = queryResult.getIntent().isEndInteraction();
            detectIntentDTO.setConversationClosed(conversationClosedDiagnosticInfo || conversationClosedIntentEndInter);
        }
        return detectIntentDTO;
    }
}


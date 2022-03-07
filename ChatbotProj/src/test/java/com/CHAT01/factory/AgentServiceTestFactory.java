package com.intesasanpaolo.bear.dcci0.chatbot.pulse.factory;

import com.intesasanpaolo.bear.dcci0.chatbot.pulse.connector.rest.google.agent.dto.*;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.model.Event;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.service.google.dto.DetectIntentDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class AgentServiceTestFactory {
    private static final DetectIntentResponse DETECT_RESPONSE_MOCK = new DetectIntentResponse();
    private static final DetectIntentResult DETECT_INTENT_RESULT_MOCK = new DetectIntentResult();
    private static final DetectIntentResultMessage DETECT_INTENT_RESULT_MESSAGE_MOCK = new DetectIntentResultMessage();
    private static final DetectIntentResultMessageText DETECT_INTENT_RESULT_MESSAGE_TEXT_MOCK = new DetectIntentResultMessageText();
    private static final DetectIntentResultIntent DETECT_INTENT_RESULT_INTENT_MOCK = new DetectIntentResultIntent();

    static {
        DETECT_INTENT_RESULT_MESSAGE_TEXT_MOCK.setText(Collections.singletonList("Ecco il link"));
        DETECT_INTENT_RESULT_MESSAGE_MOCK.setLang("it_IT");
        DETECT_INTENT_RESULT_MESSAGE_MOCK.setText(DETECT_INTENT_RESULT_MESSAGE_TEXT_MOCK);
        DETECT_INTENT_RESULT_MESSAGE_MOCK.setPayload(Collections.singletonMap("url", "http://intesasanpaolo.it"));
        DETECT_INTENT_RESULT_INTENT_MOCK.setDisplayName("RicercaMovimenti");
        DETECT_INTENT_RESULT_INTENT_MOCK.setEndInteraction(false);
        DETECT_INTENT_RESULT_INTENT_MOCK.setName("qwertyuiop");

        DETECT_INTENT_RESULT_MOCK.setParameters(null);
        DETECT_INTENT_RESULT_MOCK.setAction("get_buttons|si|NO:movimenti");
        DETECT_INTENT_RESULT_MOCK.setAllRequiredParamsPresent(true);
        DETECT_INTENT_RESULT_MOCK.setDiagnosticInfo(null);
        DETECT_INTENT_RESULT_MOCK.setFulfillmentMessages(Collections.singletonList(DETECT_INTENT_RESULT_MESSAGE_MOCK));
        DETECT_INTENT_RESULT_MOCK.setFulfillmentText("Ecco il link");
        DETECT_INTENT_RESULT_MOCK.setIntent(DETECT_INTENT_RESULT_INTENT_MOCK);
        DETECT_INTENT_RESULT_MOCK.setIntentDetectionConfidence(0.9);
        DETECT_INTENT_RESULT_MOCK.setLanguageCode("it_IT");
        DETECT_INTENT_RESULT_MOCK.setOutputContexts(Collections.emptyList());
        DETECT_INTENT_RESULT_MOCK.setQueryText("Cerco la pagina dei miei movimenti");

        DETECT_RESPONSE_MOCK.setQueryResult(DETECT_INTENT_RESULT_MOCK);
        DETECT_RESPONSE_MOCK.setResponseId("asdfghjkl");
    }

    private static final DetectIntentDTO EXPECTED_DETECT_INTENT_DTO = new DetectIntentDTO();

    static {
        EXPECTED_DETECT_INTENT_DTO.setResponseId("asdfghjkl");
        EXPECTED_DETECT_INTENT_DTO.setMessages(Collections.singletonList("Ecco il link"));
        EXPECTED_DETECT_INTENT_DTO.setLanguageCode("it_IT");
        EXPECTED_DETECT_INTENT_DTO.setIntentId("qwertyuiop");
        EXPECTED_DETECT_INTENT_DTO.setIntentName("RicercaMovimenti");
        EXPECTED_DETECT_INTENT_DTO.setConversationClosed(false);
        EXPECTED_DETECT_INTENT_DTO.setBaseEvent("movimenti");
        EXPECTED_DETECT_INTENT_DTO.setButtons(Arrays.asList("si", "NO"));
        EXPECTED_DETECT_INTENT_DTO.setParameters(null);
        EXPECTED_DETECT_INTENT_DTO.setOutputContexts(null);
        EXPECTED_DETECT_INTENT_DTO.setText("Ecco il link");
        EXPECTED_DETECT_INTENT_DTO.setAction("get_buttons");
        EXPECTED_DETECT_INTENT_DTO.setDetectionConfidence(0.9);
        EXPECTED_DETECT_INTENT_DTO.setAllRequiredParamsPresent(true);
        EXPECTED_DETECT_INTENT_DTO.setCustomPayload(Collections.singletonMap("url", "http://intesasanpaolo.it"));
    }

    private static final Event EMPTY_EVENT = new Event("WELCOME");

    private static final Event POPULATED_EVENT = new Event("WELCOME");

    static {
        POPULATED_EVENT.getEventParameters().put("params1", "val1");
    }

    private static final Event NULL_PARAMETER_EVENT = new Event("WELCOME");

    static {
        NULL_PARAMETER_EVENT.getEventParameters().put("params1", null);
    }

    public DetectIntentResponse createNonTechnicalQueryResult() {
        return DETECT_RESPONSE_MOCK;
    }

    public DetectIntentResponse createNonTechnicalQueryResultReprompt() {
        return DETECT_RESPONSE_MOCK;
    }

    public DetectIntentDTO createDetectIntentDTO() {
        return EXPECTED_DETECT_INTENT_DTO;
    }

    public Event createEventWithoutParameters() {
        return EMPTY_EVENT;
    }

    public Event createEventWithNullParameters() {
        return NULL_PARAMETER_EVENT;
    }

    public Event createEventWithParameters() {
        return POPULATED_EVENT;
    }
}

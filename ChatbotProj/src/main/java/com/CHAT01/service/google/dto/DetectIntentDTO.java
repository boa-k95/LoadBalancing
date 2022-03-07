package com.intesasanpaolo.bear.dcci0.chatbot.pulse.service.google.dto;

import com.intesasanpaolo.bear.dcci0.chatbot.pulse.model.ActiveContext;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DetectIntentDTO {
    private String text;
    private String action;
    private List<String> buttons;
    private String baseEvent;
    private String intentId;
    private String intentName;  /* Utile a fini di reportistica */
    private String responseId;
    private String languageCode;
    private List<String> messages;
    private Map<String, ? super Object> parameters;
    private boolean allRequiredParamsPresent;
    private List<ActiveContext> outputContexts;
    private boolean conversationClosed;
    private Double detectionConfidence;
    private Map<String,String> customPayload;
}

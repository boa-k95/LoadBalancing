package com.CHAT01.connector.rest.google.agent.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DetectIntentResult {
    private String queryText;
    private String action;
    // private GenericData parameters; // Probabilmente si può trasformare in una Map<String, Object>
    private Map<String, Object> parameters;
    private boolean allRequiredParamsPresent;
    private String fulfillmentText;
    private List<DetectIntentResultMessage> fulfillmentMessages;
    private List<ActiveContextDTO> outputContexts;
    private DetectIntentResultIntent intent;
    private Double intentDetectionConfidence;
    private String languageCode;
    private Map<String, Object> diagnosticInfo;
}

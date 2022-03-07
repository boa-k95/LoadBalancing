package com.CHAT01.connector.rest.google.agent.dto;

import lombok.Data;

@Data
public class DetectIntentResponse {
    private DetectIntentResult queryResult;
    private String responseId;
}

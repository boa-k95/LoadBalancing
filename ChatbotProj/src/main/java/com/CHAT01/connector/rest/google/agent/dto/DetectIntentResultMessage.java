package com.CHAT01.connector.rest.google.agent.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DetectIntentResultMessage {
    private DetectIntentResultMessageText text;
    private String lang;
    private Map<String, String> payload;
}

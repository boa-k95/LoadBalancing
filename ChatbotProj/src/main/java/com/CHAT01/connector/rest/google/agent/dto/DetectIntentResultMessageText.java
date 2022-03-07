package com.CHAT01.connector.rest.google.agent.dto;

import lombok.Data;

import java.util.List;

@Data
public class DetectIntentResultMessageText {
    private List<String> text;
}

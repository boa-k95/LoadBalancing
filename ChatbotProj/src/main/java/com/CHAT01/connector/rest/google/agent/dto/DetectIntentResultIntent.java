package com.CHAT01.connector.rest.google.agent.dto;

import lombok.Data;

@Data
public class DetectIntentResultIntent {
    private String name;
    private String displayName;
    //    private List<DialogflowContext> outputContexts;
    private boolean endInteraction; // In realtà non viene ricevuto questo campo (vedere diagnosticInfo nella queryResult)

}

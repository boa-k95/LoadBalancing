package com.CHAT01.connector.rest.google.agent.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ActiveContextDTO {
    private String lifespanCount;
    private String name;
    //    private GenericData parameters;
    private Map<String, ? super Object> parameters;
}


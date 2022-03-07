package com.CHAT01.command.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EventDTO {
    private String name;
    private Map<String, ? super Object> parameters;
}

package com.CHAT01.controller.intent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetectIntentResponse {
    private String session;
    private String text;
    private ParametersDTO parameters;
}

package com.CHAT01.controller.intent.dto;

import lombok.Data;

import java.util.List;

@Data
public class ParametersDTO {
    private String type;
    private List<? super Object> values;
}
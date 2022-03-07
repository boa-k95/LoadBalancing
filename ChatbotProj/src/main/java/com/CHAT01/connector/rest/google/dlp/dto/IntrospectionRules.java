package com.CHAT01.connector.rest.google.dlp.dto;

import lombok.Data;

import java.util.List;

@Data
public class IntrospectionRules {
    private List<InfoType> infoTypes;
    private List<IntrospectionRule> rules;
}

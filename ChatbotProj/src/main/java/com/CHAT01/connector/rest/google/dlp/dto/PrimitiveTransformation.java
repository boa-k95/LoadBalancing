package com.CHAT01.connector.rest.google.dlp.dto;

import lombok.Data;

@Data
public class PrimitiveTransformation {
    /* Mutualmente esclusive */
    private ReplaceConfig replaceConfig;
    private Object replaceWithInfoTypeConfig;
}

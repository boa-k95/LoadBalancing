package com.CHAT01.connector.rest.google.dlp.dto;

import lombok.Data;

@Data
public class HotwordProximity {
    private int windowAfter;
    private int windowBefore;
}

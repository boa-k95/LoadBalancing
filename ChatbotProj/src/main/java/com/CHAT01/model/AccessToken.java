package com.CHAT01.connector.dlp.google.token.model;

import lombok.Data;

import java.time.Instant;

@Data
public class AccessToken {
    private String token;
    private Instant tokenPreExpired;
    private Instant tokenExpired;

}

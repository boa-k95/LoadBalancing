package com.CHAT01.connector.rest.google.token.dto;

import lombok.Data;

@Data
public class AccessTokenResponse {
    private String access_token;
    private Long expires_in;
    private String token_type;
}

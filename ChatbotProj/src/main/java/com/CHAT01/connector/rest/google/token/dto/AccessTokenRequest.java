package com.CHAT01.connector.rest.google.token.dto;

import lombok.Data;

@Data
public class AccessTokenRequest {
    private String grant_type;
    private String assertion;
}

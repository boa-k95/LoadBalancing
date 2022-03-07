package com.CHAT01.connector.rest.google.token;

import com.CHAT01.connector.rest.google.token.dto.AccessTokenRequest;
import com.intesasanpaolo.bear.connector.rest.connector.BaseRestConnector;
import com.CHAT01.connector.rest.google.token.dto.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenRestConnector extends BaseRestConnector<AccessTokenRequest, AccessTokenResponse,AccessTokenRequest,AccessTokenResponse> {
}

package com.CHAT01.connector.rest.google.token.transformer;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.CHAT01.connector.rest.google.token.dto.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenRestResponseTransformer implements IRestResponseTransformer<AccessTokenResponse,AccessTokenResponse> {
    @Override
    public AccessTokenResponse transform(RestConnectorResponse<AccessTokenResponse> restConnectorResponse) {
         return restConnectorResponse.getResponse().getBody();

    }
}

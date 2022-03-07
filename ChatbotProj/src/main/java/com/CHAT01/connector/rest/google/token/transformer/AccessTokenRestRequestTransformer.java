package com.CHAT01.connector.rest.google.token.transformer;

import com.CHAT01.connector.rest.google.token.dto.AccessTokenRequest;
import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenRestRequestTransformer implements IRestRequestTransformer<AccessTokenRequest,AccessTokenRequest> {
    @Override
    public RestConnectorRequest<AccessTokenRequest> transform(AccessTokenRequest om, Object... args) {
        RestConnectorRequest<AccessTokenRequest> restConnectorRequest  = new RestConnectorRequest<>();
        restConnectorRequest.setMethod(HttpMethod.POST);
        restConnectorRequest.setRequest(om);
        restConnectorRequest.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);

    return restConnectorRequest;
    }
}

package com.CHAT01.connector.rest.google.agent.transformer;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.CHAT01.connector.rest.google.agent.dto.DetectIntentResponse;
import org.springframework.stereotype.Service;

@Service
public class DetectIntentRestResponseTransformer implements IRestResponseTransformer<DetectIntentResponse, DetectIntentResponse> {

    @Override
    public DetectIntentResponse transform(RestConnectorResponse<DetectIntentResponse> restConnectorResponse) {
        if (restConnectorResponse.getResponse() != null) {
            return restConnectorResponse.getResponse().getBody();
        } else {
            return null;
        }
    }
}

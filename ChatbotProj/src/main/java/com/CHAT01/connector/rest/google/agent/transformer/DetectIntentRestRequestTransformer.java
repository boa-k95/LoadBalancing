package com.CHAT01.connector.rest.google.agent.transformer;

import com.CHAT01.model.ChannelEnum;
import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;

import com.CHAT01.connector.rest.google.agent.dto.DetectIntentRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.CHAT01.service.google.AuthService;

@Service
public class DetectIntentRestRequestTransformer implements IRestRequestTransformer<DetectIntentRequest, DetectIntentRequest> {

    private static final String PATH_PARAMETER_SESSION_ID = "sessionId";
    private static final String DETECT_AGENT_PROJECT_ID = "detectAgentProjectId";

    @Autowired private AuthService authService;

    @Override
    public RestConnectorRequest<DetectIntentRequest> transform(DetectIntentRequest om, Object... args) {
        RestConnectorRequest<DetectIntentRequest> restConnectorRequest = new RestConnectorRequest<>();

        String detectAgentProjectId = CollectionUtils.size(args) > 0 ? (String) args[0] : null;
        String sessionId = CollectionUtils.size(args) > 1 ? (String) args[1] : null;
        ChannelEnum channel = CollectionUtils.size(args) > 2 ? (ChannelEnum) args[2] : null;
        String accessToken = authService.getAccessToken(channel);

        restConnectorRequest.setMethod(HttpMethod.POST);
        restConnectorRequest.addParams(PATH_PARAMETER_SESSION_ID, sessionId);
        restConnectorRequest.addParams(DETECT_AGENT_PROJECT_ID, detectAgentProjectId);
        restConnectorRequest.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);
        restConnectorRequest.addHeader("Authorization", "Bearer " + accessToken);

        if (om != null) {
            restConnectorRequest.setRequest(om);
        }
        return restConnectorRequest;
    }
}

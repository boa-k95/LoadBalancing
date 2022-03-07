package com.CHAT01.connector.dlp.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import com.CHAT01.model.ChannelEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.CHAT01.service.google.AuthService;
import com.CHAT01.connector.rest.google.dlp.dto.ContentServiceRequest;

@Service
public class ContentServiceRestRequestTransformer implements IRestRequestTransformer<ContentServiceRequest,ContentServiceRequest> {



        @Autowired private AuthService authService;

    // TODO: CHIEDERE PER IL CONTENT AGENT PROJECT ID
        private static final String CONTENT_AGENT_PROJECT_ID = "contentAgentProjectId";

        @Override
        public RestConnectorRequest<ContentServiceRequest> transform(ContentServiceRequest om, Object... args) {
            RestConnectorRequest<ContentServiceRequest> restConnectorRequest = new RestConnectorRequest<>();

            ChannelEnum channel = CollectionUtils.size(args) > 0 ? (ChannelEnum) args[0] : null;
            String contentAgentProjectId = CollectionUtils.size(args) > 1 ? (String) args[1] : null;

            String accessToken = authService.getAccessToken(channel);

            restConnectorRequest.setMethod(HttpMethod.POST);
            restConnectorRequest.addParams(CONTENT_AGENT_PROJECT_ID, contentAgentProjectId);
            restConnectorRequest.setRequest(om);
            restConnectorRequest.addHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);
            restConnectorRequest.addHeader("Authorization", "Bearer " + accessToken);

            return restConnectorRequest;
        }
}

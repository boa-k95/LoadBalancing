package com.CHAT01.service.google;

import com.CHAT01.connector.dlp.dto.ContentServiceRequest;
import com.CHAT01.connector.dlp.dto.ContentServiceResponse;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import com.CHAT01.service.google.factory.ContentServiceFactory;

import com.CHAT01.connector.dlp.ContentServiceRestConnector;
import com.CHAT01.connector.dlp.transformers.ContentServiceRestRequestTransformer;
import com.CHAT01.connector.dlp.transformers.ContentServiceRestResponseTransformer;
import com.intesasanpaolo.bear.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContentService extends BaseService {
    @Autowired private ContentServiceFactory contentServiceFactory;

    @Autowired private ContentServiceRestConnector contentServiceRestConnector;
    @Autowired private ContentServiceRestRequestTransformer contentServiceRestRequestTransformer;
    @Autowired private ContentServiceRestResponseTransformer contentServiceRestResponseTransformer;

    @Value("${connectors.restConfigurations.items.ContentServiceRestConnector.parameters.appProjectId}")
    private String APP_GOOGLE_PROJECT_ID;
    @Value("${connectors.restConfigurations.items.ContentServiceRestConnector.parameters.ibProjectId}")
    private String IB_GOOGLE_PROJECT_ID;
    
    public String getMaskedText(String text, ChannelEnum channel) {
        logger.info("User Original Text: '{}'", text);

        String redactedText = null;

        ContentServiceRequest contentServiceRequest = contentServiceFactory.createDefaultDLPRequest(text);
        ContentServiceResponse contentServiceResponse = null;

        /* In cerca del corretto projectId*/
        String prId = null;
        switch (channel){
            case APP: {
                prId = APP_GOOGLE_PROJECT_ID;
                break;
            }
            case IB:{
                prId = IB_GOOGLE_PROJECT_ID;
            }
        }
        String projectId = prId;

        try {
            contentServiceResponse = contentServiceRestConnector.call(contentServiceRequest, contentServiceRestRequestTransformer, contentServiceRestResponseTransformer, channel, projectId);
        } catch (Exception ex) {
            logger.error("Errore nell'elaborazione della risposta ricevuta da Google DLP");
        }

        if (contentServiceResponse != null &&
                contentServiceResponse.getItem() != null &&
                StringUtils.isNotBlank(contentServiceResponse.getItem().getValue())) {
            redactedText = contentServiceResponse.getItem().getValue();
            logger.info("User Redacted Text: '{}'", redactedText);
        }

        return redactedText;
    }

}

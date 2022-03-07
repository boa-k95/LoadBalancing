package com.CHAT01.connector.dlp.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.CHAT01.connector.rest.google.dlp.dto.ContentServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceRestResponseTransformer implements IRestResponseTransformer<ContentServiceResponse,ContentServiceResponse> {
    @Override
    public ContentServiceResponse transform(RestConnectorResponse<ContentServiceResponse> restConnectorResponse) {
         if(restConnectorResponse.getResponse() !=null){
            return restConnectorResponse.getResponse().getBody();
    } else {
        return null;
    }
}}

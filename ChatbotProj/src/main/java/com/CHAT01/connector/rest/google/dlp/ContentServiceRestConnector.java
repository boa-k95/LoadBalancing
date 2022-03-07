package com.CHAT01.connector.dlp;

import com.intesasanpaolo.bear.connector.rest.connector.BaseRestConnector;
import org.springframework.stereotype.Service;
import com.CHAT01.connector.rest.google.dlp.dto.ContentServiceRequest;
import com.CHAT01.connector.rest.google.dlp.dto.ContentServiceResponse;

@Service
public class ContentServiceRestConnector extends BaseRestConnector<ContentServiceRequest, ContentServiceResponse, ContentServiceRequest, ContentServiceResponse> {
}

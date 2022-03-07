package com.CHAT01.connector.rest.google.agent;

import com.intesasanpaolo.bear.connector.rest.connector.BaseRestConnector;
import com.CHAT01.connector.rest.google.agent.dto.DetectIntentRequest;
import com.CHAT01.connector.rest.google.agent.dto.DetectIntentResponse;
import org.springframework.stereotype.Service;

@Service
public class DetectIntentRestConnector extends BaseRestConnector<DetectIntentRequest, DetectIntentResponse, DetectIntentRequest, DetectIntentResponse>{
}

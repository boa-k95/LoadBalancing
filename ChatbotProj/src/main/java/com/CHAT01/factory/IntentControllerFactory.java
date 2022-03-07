package com.CHAT01.factory;


import com.CHAT01.command.dto.ConsoleDLPCommandInput;
import com.CHAT01.command.dto.ConsoleDLPCommandOutput;
import com.CHAT01.command.dto.DetectIntentCommandInput;
import com.CHAT01.command.dto.DetectIntentCommandOutput;
import com.CHAT01.consoleDLP.dto.ConsoleDLPRequest;
import com.CHAT01.consoleDLP.dto.ConsoleDLPResponse;
import com.CHAT01.controller.intent.dto.DetectRequest;
import com.CHAT01.controller.intent.dto.DetectResponse;
import model.Event;
import model.FrontEndInputParameters;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntentControllerFactory {
    private static final Logger logger = LoggerFactory.getLogger(IntentControllerFactory.class);

    @Autowired
    private CommonFactory factory;

    public DetectIntentCommandInput createDetectIntentCommandInput(DetectRequest request) {
        DetectIntentCommandInput input = new DetectIntentCommandInput();

        if (request != null) {
           // Customer customer = com.CHAT01.factory.createCustomer(request.getCustomer());
            //Chat chat = com.CHAT01.factory.createChat(request.getChat());
            FrontEndInputParameters frontEndInputParameters = factory.createInputParameters(request.getParameters());

            input.setSessionId(request.getSession());

            input.setText(StringUtils.trim(request.getText()));

            if (request.getEvent() != null && StringUtils.isNotBlank(request.getEvent().getName())) {
                Event event = new Event(request.getEvent().getName());
                event.setEventParameters(request.getEvent().getParameters());
                input.setEvent(event);
            } else {
                input.setEvent(null);
            }
               // input.setCustomer(customer);
            //input.setChat(chat);
            input.setChannel(request.getChannel());
            input.setFrontEndInputParameters(frontEndInputParameters);
        }

        return input;
    }

    public DetectResponse createDetectResponse(DetectIntentCommandOutput output) {
        DetectResponse response = new DetectResponse();

        if (output != null) {
//            response.setText(output.getResponse());
//            response.setParameters(output.getOutputParameters());
            response.setMessages(output.getReponses());
            response.setSession(output.getSessionId());
            response.setAck(output.getAck());
            response.setMessageNumber(output.getMessageNumber());
        }

        return response;
    }

    /* DLP */
    public ConsoleDLPCommandInput createConsoleDLPCommandInput(ConsoleDLPRequest request) {
        ConsoleDLPCommandInput consoleDLPCommandInput = new ConsoleDLPCommandInput();
        consoleDLPCommandInput.setText(request.getText());
        return consoleDLPCommandInput;
    }

    public ConsoleDLPResponse createDetectIntentResponse(ConsoleDLPCommandOutput commandOutput) {
        ConsoleDLPResponse response = new ConsoleDLPResponse();
        response.setMaskedText(commandOutput.getMaskedText());
        response.setOriginalText(commandOutput.getOriginalText());
        return response;
    }
}

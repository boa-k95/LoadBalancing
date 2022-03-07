package test.connector;


import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.CHAT01.service.google.AgentService;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;
import test.BaseTest;

import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
public class DetectIntentConnectorTest extends BaseTest {
    public static final String SESSION = "a18d45c5-3164-4e02-b507-33a87aa9f73b";

    @Autowired
    private AgentService agentService;

    private ChannelEnum channel = ChannelEnum.APP;

    @Test
    public void connectorTestOkText() {
        DetectIntentDTO expectedDetectIntentDTO = new DetectIntentDTO();
        expectedDetectIntentDTO.setAllRequiredParamsPresent(true);
        expectedDetectIntentDTO.setAction("get_random_number");
        expectedDetectIntentDTO.setText("Ecco il numero");
        expectedDetectIntentDTO.setMessages(Collections.emptyList());
        expectedDetectIntentDTO.setBaseEvent(null);
        expectedDetectIntentDTO.setConversationClosed(true);
        expectedDetectIntentDTO.setResponseId("1a2b3c4d5e");
        expectedDetectIntentDTO.setDetectionConfidence(0.666);
        expectedDetectIntentDTO.setCustomPayload(Collections.emptyMap());

        Assert.assertEquals("Wrong response (text)", expectedDetectIntentDTO,
                agentService.detectIntent(SESSION, "messaggio di test", channel));
    }


}

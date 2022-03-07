package com.intesasanpaolo.bear.dcci0.chatbot.pulse.test.connector;

import com.intesasanpaolo.bear.dcci0.chatbot.pulse.model.ChannelEnum;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.service.google.AuthService;
import com.intesasanpaolo.bear.dcci0.chatbot.pulse.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccessTokenConnectorTest extends BaseTest {
    @Autowired
    private AuthService authService;

    @Test
    public void connectorTestOkText() {
        Assert.assertNotNull("Empty token response", authService.getAccessToken(ChannelEnum.APP));
    }
}

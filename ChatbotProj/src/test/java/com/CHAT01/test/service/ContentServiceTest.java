package com.CHAT01.test.service;

import com.CHAT01.connector.dlp.dto.ContentServiceItem;
import com.CHAT01.connector.dlp.dto.ContentServiceResponse;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import com.CHAT01.service.ContentService;
import com.CHAT01.service.google.connector.ContentServiceRestConnector;
import com.CHAT01.service.google.trasfomers.inputTrasformers.ContentServiceRestRequestTransformer;
import com.CHAT01.service.google.trasfomers.outputTrasformers.ContentServiceRestResponseTransformer;
import com.CHAT01.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class ContentServiceTest extends BaseTest {
    @Autowired private ContentService contentService;

    @MockBean private ContentServiceRestConnector contentServiceRestConnector;
    @MockBean private ContentServiceRestRequestTransformer contentServiceRestRequestTransformer;
    @MockBean private ContentServiceRestResponseTransformer contentServiceRestResponseTransformer;

    @Test
    public void testMaskedTextOk() {
        ContentServiceResponse contentServiceResponse = new ContentServiceResponse();
        ContentServiceItem contentServiceItem = new ContentServiceItem();
        contentServiceItem.setValue("Ciao");
        contentServiceResponse.setItem(contentServiceItem);

        for (ChannelEnum channel : ChannelEnum.values()){
            when(contentServiceRestConnector.call(any(), contentServiceRestRequestTransformer, contentServiceRestResponseTransformer, eq(channel)))
                    .thenReturn(contentServiceResponse);
            Assert.assertEquals("Wrong response received from DLP", "Ciao", contentService.getMaskedText("Ciao", channel));
        }
    }

    @Test
    public void testMaskedTextKo() {
        for (ChannelEnum channel : ChannelEnum.values()){
            when(contentServiceRestConnector.call(any(), contentServiceRestRequestTransformer, contentServiceRestResponseTransformer, eq(channel)))
                    .thenReturn(null);
            Assert.assertEquals("Wrong response received from DLP", null, contentService.getMaskedText("Ciao", channel));
        }
    }
}

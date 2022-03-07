package test.connector;


import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.CHAT01.service.google.ContentService;
import test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class ContentServiceConnectorTest extends BaseTest {
    @Autowired
    private ContentService contentService;

    @Test
    public void connectorTestOkText() {
            Assert.assertNotNull("Empty DLP response", contentService.getMaskedText("test text", ChannelEnum.APP));
    }
}

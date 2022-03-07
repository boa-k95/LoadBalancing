package test.command;

import com.CHAT01.command.dto.DetectIntentCommandInput;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import com.CHAT01.model.ServicesCache;
import com.CHAT01.service.GenerateRandomNumberService;
import com.CHAT01.service.google.AgentService;
import com.CHAT01.service.google.ContentService;
import com.CHAT01.task.TaskService;
import com.CHAT01.task.dto.TaskInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.BaseTest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DetectIntentCommandTest  extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(DetectIntentCommandTest.class);

    protected static final String SESSION_ID = "a18d45c5-3164-4e02-b507-33a87aa9f73b";


    @MockBean  private ContentService contentService;

    @MockBean  private AgentService agentService;

    @MockBean private TaskService taskService;


    @MockBean private GenerateRandomNumberService generateRandomNumberService;

  private ChannelEnum channel;
    private DetectIntentCommandInput detectIntentCommandInputMock;
    private TaskInput default_taskInput;

    @Before
    public void init() {
        /*Utilizzo il canale APP per i test*/
        channel = ChannelEnum.APP;
        detectIntentCommandInputMock = new DetectIntentCommandInput();
        detectIntentCommandInputMock.setChannel(channel);

        default_taskInput.setFrontEndInputParameters(null);
        default_taskInput.setDetectIntentDTO(null);
       // default_taskInput.setChat(CommonTestChat.CHAT);
        default_taskInput.setCustomer(CommonTestCustomer.CUSTOMER);
        default_taskInput.setEventParameters(new HashMap<>());
        default_taskInput.setServicesCache(new ServicesCache());
        default_taskInput.setChannel(ChannelEnum.APP);
        default_taskInput.setSession(SESSION_ID);
    }

    @Test
    public void testExecuteInput(){
        detectIntentCommandInputMock.setCustomer(CommonTestCustomer.CUSTOMER);
        //detectIntentCommandInputMock.setChat(CommonTestChat.CHAT);
        detectIntentCommandInputMock.setText("numero");
        detectIntentCommandInputMock.setSessionId(SESSION_ID);
        List<String> text = new ArrayList<>();
         text.add("4");
        when(contentService.getMaskedText("Mi serve un numero", channel)).thenReturn(" Mi serve un numero");
        when(agentService.detectIntent(SESSION_ID, "Mi serve un numero", channel)).thenReturn(null);
       // when(generateRandomNumberService.createRandomNumberWithParams(text)).thenReturn(9);

    }
}

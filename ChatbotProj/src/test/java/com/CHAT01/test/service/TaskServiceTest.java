package test.service;

import com.CHAT01.command.dto.DetectIntentCommandInput;
import com.CHAT01.exceptions.ServiceException;
import com.CHAT01.model.ServicesCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;
import com.CHAT01.task.TaskService;
import com.CHAT01.task.dto.TaskOutput;
import com.CHAT01.task.impl.CompleteWithRandomNumberTextTask;
import test.BaseTest;
import test.factory.CommonTestCustomer;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceTest.class);

    private static final String EXISTING_TASK_NAME = "get_random_number";
    private static final String NONEXISTING_TASK_NAME = "get_link_abcd";
    private static final DetectIntentCommandInput DETECT_INTENT_COMMAND_INPUT_MOCK = new DetectIntentCommandInput();

    static {
        DETECT_INTENT_COMMAND_INPUT_MOCK.setText("numero");
        DETECT_INTENT_COMMAND_INPUT_MOCK.setFrontEndInputParameters(null);
        DETECT_INTENT_COMMAND_INPUT_MOCK.setCustomer(CommonTestCustomer.CUSTOMER);
        DETECT_INTENT_COMMAND_INPUT_MOCK.setSessionId(DetectInten+tCommandTest.SESSION_ID);
       // DETECT_INTENT_COMMAND_INPUT_MOCK.setChat(CommonTestChat.CHAT);
    }

    private static final DetectIntentDTO DETECT_INTENT_DTO_MOCK = new DetectIntentDTO();

    static {
        DETECT_INTENT_DTO_MOCK.setText("Non ho capito");
        DETECT_INTENT_DTO_MOCK.setBaseEvent(null);
    }

    @Autowired private TaskService taskService;
    @MockBean private CompleteWithRandomNumberTextTask getRandomNumber;

    @Before
    public void initDetectIntentDTO() {
        DETECT_INTENT_DTO_MOCK.setAction(null);
    }

    @Test
    public void testGetExecuteTaskOk() {
        DETECT_INTENT_DTO_MOCK.setAction(EXISTING_TASK_NAME);

        // Preparazione parametri di output per il test del Task
        TaskOutput taskOutput = new TaskOutput();
        taskOutput.setEvent(Optional.empty());
        taskOutput.setText(Optional.of(DETECT_INTENT_DTO_MOCK.getText()));

        try {
            when(getRandomNumber.call()).thenReturn(taskOutput);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }

        Assert.assertEquals("Wrong output received", taskOutput, taskService.getExecuteTask(DETECT_INTENT_DTO_MOCK, DETECT_INTENT_COMMAND_INPUT_MOCK, null, new ServicesCache()));
    }

    @Test
    public void testGetExecuteTaskKo() {
        DETECT_INTENT_DTO_MOCK.setAction(EXISTING_TASK_NAME);

        try {
            taskService.getExecuteTask(DETECT_INTENT_DTO_MOCK, DETECT_INTENT_COMMAND_INPUT_MOCK, null, new ServicesCache());
        } catch (Exception ex) {
            Assert.assertTrue("Wrong exception raised: " + ex.getMessage(), ex.getMessage().startsWith("Errore nell'esecuzione del com.CHAT01.task per l'action "));
        }
    }

    @Test
    public void testGetExecuteTaskKoServiceException() throws Exception {
        DETECT_INTENT_DTO_MOCK.setAction(EXISTING_TASK_NAME);

        when(getRandomNumber.call()).thenThrow(new ServiceException("TEST"));

        try {
            taskService.getExecuteTask(DETECT_INTENT_DTO_MOCK, DETECT_INTENT_COMMAND_INPUT_MOCK, null, new ServicesCache());
        } catch (Exception ex) {
            Assert.assertEquals("Wrong exception raised: " + ex.getMessage(), "TEST", ex.getMessage());
        }
    }

    @Test
    public void testGetExecuteTaskKoException() throws Exception {
        DETECT_INTENT_DTO_MOCK.setAction(EXISTING_TASK_NAME);

        when(getRandomNumber.call()).thenThrow(new IllegalArgumentException("TEST"));

        try {
            taskService.getExecuteTask(DETECT_INTENT_DTO_MOCK, DETECT_INTENT_COMMAND_INPUT_MOCK, null, new ServicesCache());
        } catch (Exception ex) {
            Assert.assertTrue("Wrong exception raised: " + ex.getMessage(), ex.getMessage().startsWith("Errore nell'esecuzione del com.CHAT01.task per l'action "));
        }
    }

    @Test
    public void testGetExecuteNonexistingTask() {
        DETECT_INTENT_DTO_MOCK.setAction(NONEXISTING_TASK_NAME);

        try {
            taskService.getExecuteTask(DETECT_INTENT_DTO_MOCK, DETECT_INTENT_COMMAND_INPUT_MOCK, null, new ServicesCache());
        } catch (Exception ex) {
            Assert.assertTrue("Wrong exception raised: " + ex.getMessage(), ex.getMessage().startsWith("No bean named "));
        }
    }
}

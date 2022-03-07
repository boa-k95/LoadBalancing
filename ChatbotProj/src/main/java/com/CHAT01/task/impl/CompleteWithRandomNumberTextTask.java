package task.impl;

import com.CHAT01.exceptions.NoRequiredEventOrTextReceived;
import com.CHAT01.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.CHAT01.service.GenerateRandomNumberService;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;
import task.Task;
import task.dto.TaskInput;
import task.dto.TaskOutput;
import task.factory.TaskFactory;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("get_random_number")
@Scope("prototype")
@RequiredArgsConstructor
public class CompleteWithRandomNumberTextTask implements Task {
    private final TaskInput input;


    @Autowired
    private TaskFactory tasksFactory;
    @Autowired
    GenerateRandomNumberService generateRandomNumberService;

    @Override
    public TaskOutput call() throws Exception {
        logger.info("Action Task '{}' called with input {}", this.getClass().getName(), input);

        TaskOutput taskOutput = new TaskOutput();

        logger.info("Action Task with text and NO event (no suffix)");

        /* Recupero Valori Parametri*/
        DetectIntentDTO params = input.getDetectIntentDTO();
        Map<String, ? super Object> serviceParamsInput = tasksFactory.createServiceInputParams(params, true);



        if (StringUtils.isBlank(input.getDetectIntentDTO().getText())) {
            throw new NoRequiredEventOrTextReceived("Non è stato ricevuto nessun testo per " + getClass().getSimpleName());
        }


        Integer generatedNumber;
        try {
            generatedNumber  = generateRandomNumberService.createRandomNumberWithParams((List<String>) serviceParamsInput);
        } catch (Exception ex) {
            throw new ServiceException("Impossibile contattare il servizio '': " + ex.getMessage(), ex.getCause());
        }
       String generatedNumberToString = String.valueOf(generatedNumber);
        String withDateText = input.getDetectIntentDTO().getText().replace("$number", generatedNumberToString   );

              taskOutput.setText(Optional.of(withDateText));
        if (taskOutput.getText().isPresent() && (taskOutput.getText().get().contains("$") || taskOutput.getText().get().contains("#"))) {
            logger.warn("Incomplete text for the user: {}", taskOutput.getText().get());
        }
  /* Se devo restituire del testo la comunicazione è non tecnica e non devo inviare eventi a Dialogflow */
        taskOutput.setEvent(Optional.empty());
        taskOutput.setContext(Optional.empty());


        return taskOutput;
    }
}
package com.CHAT01.task.factory;

import com.CHAT01.command.dto.DetectIntentCommandInput;
import org.springframework.stereotype.Component;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;
import com.CHAT01.task.dto.TaskInput;

@Component
public class TaskServiceFactory {

 public TaskInput createTaskInput(DetectIntentDTO detectIntentDTO, DetectIntentCommandInput commandInput) {
            TaskInput input = new TaskInput();
            input.setDetectIntentDTO(detectIntentDTO);
            input.setFrontEndInputParameters(commandInput.getFrontEndInputParameters());
            input.setCustomer(commandInput.getCustomer());
           // input.setChat(commandInput.getChat());
            input.setChannel(commandInput.getChannel());
            return input;

}}

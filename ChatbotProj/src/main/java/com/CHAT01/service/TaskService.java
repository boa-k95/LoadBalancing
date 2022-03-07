package com.CHAT01.service;

import com.CHAT01.task.factory.TaskServiceFactory;
import com.CHAT01.task.generic.Task;
import com.intesasanpaolo.bear.service.BaseService;
import com.CHAT01.command.dto.DetectIntentCommandInput;
import com.CHAT01.exception.InvalidTaskResponseException;
import com.CHAT01.exception.ServiceException;
import com.CHAT01.model.ServicesCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CHAT01.service.google.agent.dto.DetectIntentDTO;
import com.CHAT01.task.dto.TaskInput;
import com.CHAT01.task.dto.TaskOutput;

import java.util.Map;

@Service
public class TaskService extends BaseService {

    @Autowired private BeanFactory beanFactory;
    @Autowired private TaskServiceFactory taskServiceFactory;


/**
 * @param detectIntentDTO
 * @param **originalEventParams
 * @param servicesCache       Map<String, ? extends Object> that contains the result of the call of services for the entire Command life
 * @return
 */
public TaskOutput getExecuteTask(DetectIntentDTO detectIntentDTO, DetectIntentCommandInput commandInput, Map<String, ? super Object> originalEventParams, ServicesCache servicesCache)
        throws BeansException, ServiceException, InvalidTaskResponseException {
    TaskOutput output = null;
    TaskInput taskInput = taskServiceFactory.createTaskInput(detectIntentDTO, commandInput);

    /* Propagazione dei parametri per un eventuale evento generato */
    taskInput.setSession(commandInput.getSessionId());
   taskInput.setEventParameters(originalEventParams);
    taskInput.setServicesCache(servicesCache);

    Task task;
    try {
        task = (Task) beanFactory.getBean(detectIntentDTO.getAction(), taskInput);
    } catch (BeansException ex) {
        logger.error("Nessun Bean trovato per l'action {}", detectIntentDTO.getAction());
        throw ex;
    }

    try {
        output = task.call();
    } catch (ServiceException ex) {
        logger.error("Errore nell'invocazione di un servizio durante l'eseguzione del com.CHAT01.task per {}: '{}'", detectIntentDTO.getAction(), ex.getMessage());
        throw ex;
    } catch (Exception e) {
        logger.error("Eccezione nell'invocazione del com.CHAT01.task per {}: '{}' con causa '{}'", detectIntentDTO.getAction(), e.getMessage(), e.getCause());
    }

    if (output == null) {
        logger.error("Errore durante l'esecuzione del com.CHAT01.task per l'action {} con input\nDetectIntentDTO: {}\nCommandInput: {}\nEvent Parameters: {}\n ServiceCache: {}",
                detectIntentDTO.getAction(), detectIntentDTO, commandInput, originalEventParams, servicesCache);
        throw new InvalidTaskResponseException("Errore nell'esecuzione del com.CHAT01.task per l'action " + detectIntentDTO.getAction());
    }


    output.setTaskInput(taskInput);

    return output;
}

}

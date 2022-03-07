package com.CHAT01.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.CHAT01.task.dto.TaskOutput;

import java.util.concurrent.Callable;

public interface Task extends Callable<TaskOutput> {
    Logger logger = LoggerFactory.getLogger(Task.class);
}

package com.example.secondService.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Command<R> {
     protected   final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected boolean canExecute() throws Exception {
        return true;
    }

    protected R doExecute() throws Exception {
        return null;
    }
}

package com.example.chatbotApplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "employee")
public class firstController {

    @Autowired
    private Environment environment;
   private static final Logger logger = LoggerFactory.getLogger(firstController.class);
    @GetMapping(value ="/message")
    public String displayMessage(){
        String serverPort = environment.getProperty("local.server.port");
   System.out.println("Port : " + serverPort);

        logger.info("calling the displayMessage");
        return "Hello im calling the FIRST SERVICE " + serverPort;
    }

}

package com.example.secondService.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consumer")
public class secondController {
    private static final Logger logger = LoggerFactory.getLogger(secondController.class);


    @Value("${my.greeting")
    private String welocmeMessage;

    @GetMapping("/message")
    public ResponseEntity<String> showMessage(){
        logger.info(welocmeMessage);

        String getStringfromjInput = new String("Kurtis");


        logger.info("im calling the second service !!!!!!!!!!!!!!!!!!");
        return ResponseEntity.ok(new String("im calling the second service"));
    }
}

package com.example.chatbotApplication.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.chatbotApplication")
@EnableEurekaClient
public class chatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(chatbotApplication.class, args);
	}

}

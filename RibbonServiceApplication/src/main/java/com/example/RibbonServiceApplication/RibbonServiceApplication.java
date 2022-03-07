package com.example.RibbonServiceApplication;

import com.example.Configuration.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.ribbon.RibbonClient;


@SpringBootApplication(scanBasePackages= {"com.netflix.client.config.IClientConfig"})
@RibbonClient(name="demo",configuration=RibbonConfiguration.class)
public class RibbonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbonServiceApplication.class, args);
	}


}

package com.example.Configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@EnableAutoConfiguration
@Configuration
public class Template {
    @LoadBalanced
    @Bean
    protected RestTemplate getRestTemplate(){
        return new RestTemplate();}
}

package com.example.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {
  @Autowired
    private IClientConfig iClientConfig;

    @Bean
        public IPing ribbonPing() {
        return new PingUrl();
    }

    public IRule ribbonRule() {
         return  new WeightedResponseTimeRule();
     }


}

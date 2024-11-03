package com.newsdata.io_accessor.newsdata_io_accessor.BL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

@Configuration
public class DaprConfig {
   @Bean
    public DaprClient daprBuild(){
        
        return new DaprClientBuilder().build();
    }
}

package com.data_manager.data_manager.BL;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

@Configuration
public class ConfigurationClass {
   @Bean
    public DaprClient daprBuild(){
        return new DaprClientBuilder().build();
    }

    @Bean
    public ObjectMapper getMapper(){
        return new ObjectMapper();
    }
      @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.modules(new JavaTimeModule());
    }
}

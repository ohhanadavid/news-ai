package com.mail_sender_engine.mail_sender_engine.BL;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



@Configuration
public class ConfigurationClass {


    @Bean
    public ObjectMapper getMapper(){
        return new ObjectMapper();
    }
      @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.modules(new JavaTimeModule());
    }
}

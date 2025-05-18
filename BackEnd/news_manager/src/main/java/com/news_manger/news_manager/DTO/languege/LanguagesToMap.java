package com.news_manger.news_manager.DTO.languege;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LanguagesToMap {

    @Value("classpath:languages.json")
    private org.springframework.core.io.Resource resource;

    @Bean
    public Map<String, String> languageMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<HashMap<String, String>>() {});
        }
    }
}


package com.newsdata.io_accessor.newsdata_io_accessor.DAL.languages;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Langueges {

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


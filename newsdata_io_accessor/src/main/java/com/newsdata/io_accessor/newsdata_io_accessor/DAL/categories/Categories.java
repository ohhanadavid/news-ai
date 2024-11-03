package com.newsdata.io_accessor.newsdata_io_accessor.DAL.categories;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableConfigurationProperties(CategoriesList.class)
public class Categories {
    @Value("classpath:categories.json")
    private org.springframework.core.io.Resource resource;

    @SuppressWarnings("unchecked")
    @Bean
    @Primary
    public CategoriesList categoriesConfig(ObjectMapper objectMapper) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            Map<String, Object> map = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            
            return new CategoriesList((List<String>) map.get("categories"));
        }
    }
}
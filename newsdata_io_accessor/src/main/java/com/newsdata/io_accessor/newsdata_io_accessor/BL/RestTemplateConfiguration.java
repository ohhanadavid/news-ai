package com.newsdata.io_accessor.newsdata_io_accessor.BL;

// import java.time.LocalDateTime;

// import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.module.SimpleModule;
// import com.newsdata.io_accessor.newsdata_io_accessor.DAL.ArticalDataTimeFormatter;

@Configuration
public class RestTemplateConfiguration {
    // @Bean
    // public RestTemplate restTemplate(RestTemplateBuilder builder){
    //         ObjectMapper objectMapper = new ObjectMapper();
    //     SimpleModule module = new SimpleModule();
    //     module.addDeserializer(LocalDateTime.class, new ArticalDataTimeFormatter());
    //     objectMapper.registerModule(module);

    //     MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    //     messageConverter.setObjectMapper(objectMapper);

    //     return builder
    //             .additionalMessageConverters(messageConverter)
    //             .build();
    // }
    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(objectMapper));
        return restTemplate;
    }
}

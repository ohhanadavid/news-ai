package com.newsdata.io_accessor.newsdata_io_accessor.configuration;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestTemplate;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Log4j2
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
            log.info("Sending request to: {} {}", outReq.getMethod(), outReq.getURI());
            return clientHttpReqExec.execute(outReq, bytes);
        });
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(objectMapper));
        return restTemplate;
    }
}
//@Configuration
//public class RestTemplateConfiguration {
//
//    @Bean
//    @RequestScope
//    public RestTemplate getRestTemplate(HttpServletRequest inReq) {
//        final String authHeader =
//                inReq.getHeader(HttpHeaders.AUTHORIZATION);
//        final RestTemplate restTemplate = new RestTemplate();
//        if (authHeader != null && !authHeader.isEmpty()) {
//            restTemplate.getInterceptors().add(
//                    (outReq, bytes, clientHttpReqExec) -> {
//                        outReq.getHeaders().set(
//                                HttpHeaders.AUTHORIZATION, authHeader
//                        );
//                        return clientHttpReqExec.execute(outReq, bytes);
//                    });
//        }
//        return restTemplate;
//    }


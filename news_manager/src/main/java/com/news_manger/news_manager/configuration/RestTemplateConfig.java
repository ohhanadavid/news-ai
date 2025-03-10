package com.news_manger.news_manager.configuration;

import javax.servlet.http.HttpServletRequestWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Log4j2
@Configuration
@Import(ServletWebServerFactoryAutoConfiguration.class)
public class RestTemplateConfig {

//    @Bean
//    @RequestScope
//    public RestTemplate getRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
//            log.info("Sending request to: {} {}", outReq.getMethod(), outReq.getURI());
//            return clientHttpReqExec.execute(outReq, bytes);
//        });
//
//        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attrs != null) {
//            jakarta.servlet.http.HttpServletRequest inReq = attrs.getRequest();
//
//            if (inReq instanceof HttpServletRequestWrapper) {
//                inReq = (HttpServletRequest) ((HttpServletRequestWrapper) inReq).getRequest();
//            }
//
//            final String authHeader = inReq.getHeader(HttpHeaders.AUTHORIZATION);
//            if (authHeader != null && !authHeader.isEmpty()) {
//                restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
//                    outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);
//                    return clientHttpReqExec.execute(outReq, bytes);
//                });
//            } else {
//                log.warn("No Authorization header found in incoming request.");
//            }
//        } else {
//            log.warn("No incoming request found.");
//        }
//
//        return restTemplate;
//    }

    @Bean
    @Qualifier("withoutToken")
    public RestTemplate restTemplateWithoutToken(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
            log.info("Sending request to: {} {}", outReq.getMethod(), outReq.getURI());
            return clientHttpReqExec.execute(outReq, bytes);
        });

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(objectMapper));
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
            log.info("Sending request to: {} {}", outReq.getMethod(), outReq.getURI());
            return clientHttpReqExec.execute(outReq, bytes);
        });
        restTemplate.getInterceptors().add(bearerTokenInterceptor());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(objectMapper));
        return restTemplate;
    }


    @Bean
    public ClientHttpRequestInterceptor bearerTokenInterceptor() {
        return new BearerTokenInterceptor();
    }


    @Bean
    public TokenStorage tokenStorage() {
        return new TokenStorage();
    }
}



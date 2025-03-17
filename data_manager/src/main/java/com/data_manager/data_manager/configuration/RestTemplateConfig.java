package com.data_manager.data_manager.configuration;


import javax.servlet.http.HttpServletRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.ArrayList;
import java.util.List;

@Configuration
@Import(ServletWebServerFactoryAutoConfiguration.class)
public class RestTemplateConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    @RequestScope
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
            logger.info("Sending request to: {} {}", outReq.getMethod(), outReq.getURI());
            return clientHttpReqExec.execute(outReq, bytes);
        });

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest inReq = attrs.getRequest();

            if (inReq instanceof HttpServletRequestWrapper) {
                inReq = (HttpServletRequest) ((HttpServletRequestWrapper) inReq).getRequest();
            }

            final String authHeader = inReq.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && !authHeader.isEmpty()) {
                restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
                    outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);
                    return clientHttpReqExec.execute(outReq, bytes);
                });
            } else {
                logger.warn("No Authorization header found in incoming request.");
            }
        } else {
            logger.warn("No incoming request found.");
        }

        return restTemplate;
    }

}

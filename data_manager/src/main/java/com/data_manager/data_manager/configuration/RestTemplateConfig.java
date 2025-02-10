package com.data_manager.data_manager.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@Import(ServletWebServerFactoryAutoConfiguration.class)
public class RestTemplateConfig {

    @Bean
    @RequestScope
    public RestTemplate getRestTemplate(ObjectProvider<HttpServletRequest> requestProvider) {
        HttpServletRequest inReq = requestProvider.getIfAvailable();
        RestTemplate restTemplate = new RestTemplate();

        if (inReq != null) {
            final String authHeader = inReq.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && !authHeader.isEmpty()) {
                restTemplate.getInterceptors().add(
                        (outReq, bytes, clientHttpReqExec) -> {
                            outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);
                            return clientHttpReqExec.execute(outReq, bytes);
                        });
            }
        }

        return restTemplate;
    }
}

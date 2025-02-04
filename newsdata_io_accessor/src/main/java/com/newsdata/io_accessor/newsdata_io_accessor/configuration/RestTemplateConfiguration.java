package com.newsdata.io_accessor.newsdata_io_accessor.configuration;

// import java.time.LocalDateTime;

// import org.springframework.boot.web.client.RestTemplateBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.context.annotation.RequestScope;
// import com.fasterxml.jackson.databind.module.SimpleModule;
// import com.newsdata.io_accessor.newsdata_io_accessor.DAL.ArticalDataTimeFormatter;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    @RequestScope
    public RestTemplate getRestTemplate(HttpServletRequest inReq) {
        final String authHeader =
                inReq.getHeader(HttpHeaders.AUTHORIZATION);
        final RestTemplate restTemplate = new RestTemplate();
        if (authHeader != null && !authHeader.isEmpty()) {
            restTemplate.getInterceptors().add(
                    (outReq, bytes, clientHttpReqExec) -> {
                        outReq.getHeaders().set(
                                HttpHeaders.AUTHORIZATION, authHeader
                        );
                        return clientHttpReqExec.execute(outReq, bytes);
                    });
        }
        return restTemplate;
    }
}

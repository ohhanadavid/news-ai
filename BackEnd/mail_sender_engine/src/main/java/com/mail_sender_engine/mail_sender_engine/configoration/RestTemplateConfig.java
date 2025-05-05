package com.mail_sender_engine.mail_sender_engine.configoration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class RestTemplateConfig {

    @Value("${twilio.account.sid}")
    String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    String AUTH_TOKEN;

    @Value("${twilio.whatsapp.number}")
    private String TWILIO_WHATSAPP_NUMBER;

    @Value("${twilio_template")
    private String TWILIO_TEMPLATE;

    @Bean
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


    @Bean
    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = ACCOUNT_SID + ":" + AUTH_TOKEN;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
//        headers.setBasicAuth(ACCOUNT_SID, AUTH_TOKEN);
        return headers;
    }
}

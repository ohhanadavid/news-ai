package com.mail_sender_engine.mail_sender_engine.BL;

import com.mail_sender_engine.mail_sender_engine.DAL.NotificatioData;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.content.v1.Content;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.twilio.converter.Promoter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@Log4j2
public class WhatsAppService {
    @Value("${twilio.account.sid}")
    String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    String AUTH_TOKEN;

    @Value("${twilio.whatsapp.number}")
    private String TWILIO_WHATSAPP_NUMBER;

    @Value("${twilio_template}")
    private String TWILIO_TEMPLATE;

    @Autowired
    RestTemplate  restTemplate;



    @Autowired
    private HttpHeaders headers;


    public String sendWhatsAppMessage(NotificatioData data) {


        log.info("sendWhatsAppMessage to {} ",
                data.getConnectInfo());
        if(data.getConnectInfo() == null || data.getConnectInfo().isEmpty()) {
            log.error("WhatsApp number is null or empty");
            return null;
        }
        if(ACCOUNT_SID.isEmpty()|| AUTH_TOKEN.isEmpty() || TWILIO_WHATSAPP_NUMBER.isEmpty()) {
            log.error("Twilio credentials are not set");
            return null;
        }

        String phone= data.getConnectInfo();
        if (!phone.startsWith("+972")) {
            phone = "+972" + phone.substring(1);
        }

        String fromWhatsAppNumber = "whatsapp:" + TWILIO_WHATSAPP_NUMBER;
        String toWhatsAppNumber = "whatsapp:" + phone;
        String url = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/Messages.json";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("To", toWhatsAppNumber);
        body.add("From", fromWhatsAppNumber);
        body.add("ContentSid", TWILIO_TEMPLATE);
        String variablesJson = String.format("{\"1\":\"%s\", \"2\":\"%s\"}",
                escapeJson(data.getSubject()+"\n\n\n"),
                escapeJson(data.getText()));

        body.add("ContentVariables", variablesJson);


        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());



        log.info("whatsapp sent to {} with sid {}", data.getConnectInfo(), response.getStatusCode());
        return response.toString();
    }

    private String escapeJson(String input) {
        return input.replace("\"", "\\\"").replace("\n", "\\n");
    }
}

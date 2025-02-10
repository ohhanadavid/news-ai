package com.mail_sender_engine.mail_sender_engine.BL;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail_sender_engine.mail_sender_engine.DAL.MailData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class MailController {
    @Autowired
    EmailService mailService;
    @Autowired
    ObjectMapper om;

    @PostMapping("api.sendMail")
    @KafkaListener(topics={"api.sendMail"})
    //public void sendEmail(@RequestBody MailData data){
    public void sendEmail(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        log.info("api.sendMail");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            MailData data = om.readValue(message.toString(), MailData.class);
            if (mailService.sentEmail(data))
                log.info("email to {} send", data.getEmail());
        }


    }


}

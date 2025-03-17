package com.mail_sender_engine.mail_sender_engine.BL;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail_sender_engine.mail_sender_engine.DAL.NotificatioData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class NotificationConsumer {
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
            NotificatioData data = om.readValue(message.toString(), NotificatioData.class);
            if (mailService.sentEmail(data))
                log.info("email to {} send", data.getConnectInfo());
        }


    }
    @Autowired
    SmsService smsService;

    @KafkaListener(topics = {"api.sms"})
    public void smsAll(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            NotificatioData data = om.readValue(message.toString(), NotificatioData.class);
            smsService.send(data.getText(), data.getConnectInfo());
                log.info("sms to {} send", data.getConnectInfo());
        }


    }
    @Autowired
    WhatsAppService whatsAppService;

    @KafkaListener(topics = {"api.whatsApp"})
    public void whatsAppAll(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            NotificatioData data = om.readValue(message.toString(), NotificatioData.class);
            whatsAppService.sendWhatsAppMessage(data);
            log.info("whatsApp to {} send", data.getConnectInfo());
        }


    }

}

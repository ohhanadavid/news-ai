package com.mail_sender_engine.mail_sender_engine.BL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail_sender_engine.mail_sender_engine.DAL.ClientMail;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class MailService {
     @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    String from;
    @Autowired
    ObjectMapper objectMapper;

    public ResponseEntity<?> sendArticlsMessage(Map<String,Object> data) {
        try{
            ClientMail to=objectMapper.convertValue(data.get("to"),ClientMail.class);
            String article=(String)data.get("articls");
            String text=String.format("Hi %s, here are your articles:\n\n\n\n%s",to.getName(),article);
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom(from);
            message.setTo(to.getEmail()); 
            message.setSubject(String.format("Your article")); 
            message.setText(text);
            emailSender.send(message);
            return new ResponseEntity<>("The email send!",HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    public ResponseEntity<?> test(String data) {
        try{
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom(from);
            message.setTo(data); 
            message.setSubject(String.format("Your article")); 
            message.setText("text");
            emailSender.send(message);
            return new ResponseEntity<>("The email send!",HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}

package com.mail_sender_engine.mail_sender_engine.BL;
import java.util.Map;

import com.mail_sender_engine.mail_sender_engine.DAL.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("api.sendMail")
    public ResponseEntity<?> sendEmail(@RequestBody MailData data){
        log.info("api.sendMail");
        try{
            if(mailService.sentEmail(data)) {
                log.info("email to {} send", data.getEmail());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.error("email to {} fail", data.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping()
    public Boolean healthCheck(){
        return true;
    }

}

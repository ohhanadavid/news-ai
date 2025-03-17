package com.mail_sender_engine.mail_sender_engine.BL;
import java.io.IOException;

import com.mail_sender_engine.mail_sender_engine.DAL.NotificatioData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;


@Service
@Log4j2
public class EmailService {
    @Value("${spring.sendgrid.api.key}")
    private  String api_key;

    public Boolean sentEmail(NotificatioData data)  {
        Email from = new Email("davidohhana@gmail.com");
        String subject = data.getSubject();
        Email to = new Email(data.getConnectInfo());
        Content content = new Content("text/plain", data.getText());
        Mail mail = new Mail(from, subject, to, content);

        if (api_key == null || api_key.trim().isEmpty()) {
            throw new IllegalStateException("SendGrid API key not configured!");
        }
        log.info(api_key);
        SendGrid sg = new SendGrid(api_key);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info(response.getStatusCode());
            log.info(response.getBody());
            log.info(response.getHeaders());
        } catch (IOException ex) {
           log.error(ex);
            return false;
        }
        return true;
    }
}

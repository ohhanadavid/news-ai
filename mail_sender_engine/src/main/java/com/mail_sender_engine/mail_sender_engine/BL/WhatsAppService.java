package com.mail_sender_engine.mail_sender_engine.BL;

import com.mail_sender_engine.mail_sender_engine.DAL.NotificatioData;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Service
public class WhatsAppService {
    @Value("${twilio.account.sid}")
    String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    String AUTH_TOKEN;

    @Value("${twilio.whatsapp.number}")
    private String TWILIO_WHATSAPP_NUMBER;

    public void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public String sendWhatsAppMessage(NotificatioData data) {
        String phone= data.getConnectInfo();
        if (!phone.startsWith("+972")) {
            phone = "+972" + phone.substring(1);
        }

        init();

        String fromWhatsAppNumber = "whatsapp:" + TWILIO_WHATSAPP_NUMBER;
        String toWhatsAppNumber = "whatsapp:" + phone;

        Message message = Message.creator(
                        new PhoneNumber(toWhatsAppNumber),
                        new PhoneNumber(fromWhatsAppNumber),
                        data.getSubject()+"/n"+data.getText())
                .create();

        return message.getSid();
    }
}

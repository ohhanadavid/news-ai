package com.mail_sender_engine.mail_sender_engine.BL;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


// copied from documentation https://www.sms4free.co.il/outcome-sms-api.html
@Service
public class SmsService {
    protected final Log logger = LogFactory.getLog(getClass());
    OkHttpClient client = new OkHttpClient.Builder().build();

    @Value("${sms4free.key}")
    private String ACCOUNT_KEY;
    @Value("${sms4free.user}")
    private String ACCOUNT_USER;
    @Value("${sms4free.password}")
    private String ACCOUNT_PASS;





    public boolean send(String text, String phoneNumber) {

        if (phoneNumber == null) return false;
//        if (phoneNumber.startsWith("+972")) phoneNumber = phoneNumber.replaceAll("\\+972","0");
        MediaType mediaType = MediaType.parse("application/json");
        //Define The URL We Wanna Post to
        String url = "https://api.sms4free.co.il/ApiSMS/v2/SendSMS";
        URL urlObj = null;
        try { urlObj = new URL(url); } catch (MalformedURLException e) {}

        String key = ACCOUNT_KEY;
        String user = ACCOUNT_USER;
        String pass = ACCOUNT_PASS;
        try {

            //create the form body as json
            String json = "{\"key\":\"" + key + "\",\"user\":\"" + user + "\",\"pass\":\"" + pass
                    + "\",\"sender\":\"" + "0543331719" + "\",\"recipient\":\"" + phoneNumber + "\",\"msg\":\"" + text + "\"}";

            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", RequestBody.create(mediaType, json))
                    .addHeader("content-type", "application/json")
                    .build();

            Response res = client.newCall(request).execute();
            String data = res.body().string();
            System.out.println(res.code());
            System.out.println(data);
            //print result
            boolean success = res.code() < 300  && res.code() >= 200;

            return success;
        }catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}

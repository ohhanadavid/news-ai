package com.news_manger.news_manager.BL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import lombok.extern.log4j.Log4j2;



@Service
@Log4j2
public class CheckingData implements IChecking {

    @Autowired
    private  DaprClient daprClient;
     @Value("${UserAccessorUrl}")
    String userAccessorUrl;
    @Override
    public <T> ResponseEntity<?> checkResponse(ResponseEntity<?> response,Class<T> expectedType ){
        log.info("checkData");
        if(response == null)
            return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
        
        if(!(expectedType.isInstance(response.getBody() )))
            return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

     @Override
    public ResponseEntity<?> checkUser(String email){
        String url=String.format("api.userExists/%s",email);
        
        Boolean e=daprClient.invokeMethod(userAccessorUrl, url,null ,HttpExtension.GET,Boolean.class).block();
        ResponseEntity<?> exists=ResponseEntity.ok(e);
        return exists;    
 
    }
}

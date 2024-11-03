package com.news_manger.news_manager.BL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.news_manger.news_manager.DAL.user.User;
import com.news_manger.news_manager.Exception.ItemNotFoundException;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService implements IUserService {
    @Autowired
    DaprClient daprClient;
    @Value("${UserAccessorUrl}")
    String userAccessorUrl;


    @Override
    public ResponseEntity<?> getUser( String email) {
        log.info("getUser request");
        try{
            String url=String.format("api.getUser/%s", email);
            var response=daprClient.invokeMethod(userAccessorUrl, url,email, HttpExtension.GET, User.class).block();
            if(response == null){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else {
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            
         
        }catch(ItemNotFoundException e){
            log.error("getUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            log.error("getUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    
  

}

package com.data_manager.data_manager.BL.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.data_manager.data_manager.DAL.user.User;
import com.data_manager.data_manager.Exception.ItemNotFoundException;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import io.dapr.client.domain.InvokeBindingRequest;
import io.dapr.utils.TypeRef;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService implements IUserService {
    @Autowired
    DaprClient daprClient;
    @Value("${UserAccessorUrl}")
    String userAccessorUrl;
    @Autowired
    IChecking checking;

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
    
    public ResponseEntity<?> saveUser( User user){
        log.info("creatUser request");
        try{
            ResponseEntity<?> check=checking.checkUser(user.getEmail());
            if(check.getStatusCode()!= HttpStatus.OK ||(boolean)check.getBody() )
                return  check;
            InvokeBindingRequest b = new InvokeBindingRequest("api.createUser", "create");
            b.setData(user);
            daprClient.invokeBinding(b, TypeRef.BOOLEAN);
            daprClient.invokeBinding("api.createUser", "create", user).block();

            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        catch(Exception e)
        {
            log.error("creatUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }

   

    public ResponseEntity<?> deleteUser( String email){
        log.info("deleteUser request");
        try{
            ResponseEntity<?> check=checking.checkUser(email);
            if(check.getStatusCode()!= HttpStatus.OK ||!(boolean)check.getBody() )
                return  check;
            Map<String,String> data = new HashMap<>();
            data.put("email",email);
            InvokeBindingRequest b = new InvokeBindingRequest("api.createUser", "create");
            b.setData(data);
            daprClient.invokeBinding(b, TypeRef.BOOLEAN);
            daprClient.invokeBinding("api.deleteUser", "create",data).block();
            return new ResponseEntity<>(email+" deleted!",HttpStatus.OK);
            
        }
        catch(Exception e)
        {
            log.error("deleteUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }


    public ResponseEntity<?> updateUserName( User user) {
        log.info("updateName request");
        try{
            ResponseEntity<?> check=checking.checkUser(user.getEmail());
            if(check.getStatusCode()!= HttpStatus.OK ||!(boolean)check.getBody() )
                return  check;
            InvokeBindingRequest b = new InvokeBindingRequest("api.createUser", "create");
            b.setData(user);
            daprClient.invokeBinding(b, TypeRef.BOOLEAN);
            daprClient.invokeBinding("api.updateUserName", "create", user).block();
           
            return new ResponseEntity<>("user name update!",HttpStatus.OK);
            
        }

        catch(Exception e)
        {
            log.error("updateName request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }


    public ResponseEntity<?> updateUserMail( String oldEmail,String newEmail) {
        log.info("updateMail request");
        try{
            ResponseEntity<?> check=checking.checkUser(oldEmail);
            if(check.getStatusCode()!= HttpStatus.OK ||!(boolean)check.getBody() )
                return  check;
            check=checking.checkUser(newEmail);
            if(check.getStatusCode()!= HttpStatus.OK ||(boolean)check.getBody() )
                return  check;
            
            Map<String, String> data = new HashMap<>();
            data.put("newEmail", newEmail);
            data.put("oldEmail", oldEmail);
            InvokeBindingRequest b = new InvokeBindingRequest("api.createUser", "create");
            b.setData(data);
            daprClient.invokeBinding(b, TypeRef.BOOLEAN);                           
            daprClient.invokeBinding("api.updateUserMail", "create", data ).block();
          
            return new ResponseEntity<>("mail update!",HttpStatus.OK);
            
        }
        catch(Exception e)
        {
            log.error("updateMail request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }

}

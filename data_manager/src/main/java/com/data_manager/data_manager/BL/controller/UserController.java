package com.data_manager.data_manager.BL.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data_manager.data_manager.BL.services.UserService;
import com.data_manager.data_manager.DAL.user.User;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping()
    public Boolean healthCheck(){
        return true;
    }

  @GetMapping("getUser/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        log.info("getUser request");
        try{
            return userService.getUser(email);
            
         
        }catch(Exception e)
        {
            log.error("getUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }
        
    @PostMapping("saveUser")
    public ResponseEntity<?> creatUser(@RequestBody User user){
        log.info("creatUser request");
        try{
            return userService.saveUser(user);
            
        }
        catch(Exception e)
        {
            log.error("creatUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String email){
        log.info("deleteUser request");
        try{
            return userService.deleteUser(email);
            
        }

        catch(Exception e)
        {
            log.error("deleteUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }

    @PutMapping("updateName")
    public ResponseEntity<?> updateUserName(@RequestBody User user) {
        log.info("updateName request");
        try{
            return userService.updateUserName(user);
        }catch(Exception e)
        {
            log.error("updateName request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }

    @PutMapping("updateMail/{oldEmail}")
    public ResponseEntity<?> updateUserMail(@PathVariable String oldEmail,@RequestParam String newEmail) {
        log.info("updateMail request");
        try{
            return userService.updateUserMail(oldEmail, newEmail);
            
        } catch(Exception e)
        {
            log.error("updateMail request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }
}

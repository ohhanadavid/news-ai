package com.data_manager.data_manager.BL.controller;

import com.data_manager.data_manager.DAL.user.UserOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data_manager.data_manager.BL.services.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class UserController {
    @Autowired
    UserService userService;


  @GetMapping("getUser/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        log.info("getUser request");
        //try{
            return new ResponseEntity<>( userService.getUserOut(email),HttpStatus.OK);
            
         
//        }catch(Exception e)
//        {
//            log.error("getUser request {}",e.getMessage());
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String email){
        log.info("deleteUser request");
        //try{
            return userService.deleteUser(email);
            
//        }
//
//        catch(Exception e)
//        {
//            log.error("deleteUser request {}",e.getMessage());
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
    }

    @PutMapping("updateName")
    public ResponseEntity<?> updateUserName(@RequestBody UserOut user) {
        log.info("updateName request");
        //try{
            return userService.updateUserName(user);
//        }catch(Exception e)
//        {
//            log.error("updateName request {}",e.getMessage());
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
    }


}

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
@RestController("dataManager/userData")
public class UserController {
    @Autowired
    UserService userService;


  @GetMapping("getUser/{email}")
    public UserOut getUser(@PathVariable String email) {
        log.info("getUser request");
        return userService.getUserOut(email);
    }

    @DeleteMapping("deleteUser")
    public String deleteUser(@RequestParam String email){
        log.info("deleteUser request");

            return userService.deleteUser(email);

    }

    @PutMapping("updateName")
    public String updateUserName(@RequestBody UserOut user) {
        log.info("updateName request");

            return userService.updateUserName(user);

    }


}

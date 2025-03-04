package com.user_accessor.user_accessor.BL;


import java.util.concurrent.CompletableFuture;

import com.user_accessor.user_accessor.DAL.user.User;
import com.user_accessor.user_accessor.DAL.user.UserData;
import com.user_accessor.user_accessor.DAL.user.UserIn;
import com.user_accessor.user_accessor.DAL.user.UserOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.user_accessor.user_accessor.Exception.ItemNotFoundException;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    IUserKeyAction categoryService;
    @Autowired
    IUserKeyAction languageService;

    @GetMapping("/api.getUser")
    public UserOut getUseOut(@AuthenticationPrincipal Jwt jwt) {
        log.info("getUserOut request");
        UserOut user= userService.getUserOut(new UserData(jwt));
        log.info("getUserOut request {} found!",user);
        if(user==null){
            throw new ItemNotFoundException("user not found");
        }
        return user;

    }

    @PostMapping("/api.createUser")
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        log.info("createUser request");
        User user=userService.creteUser(newUser);
        log.info("createUser request user accessed!");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/api.deleteUser")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal Jwt jwt){
        UserData userData = new UserData(jwt);
        log.info("deleteUser {} request",userData.getUserID());

        CompletableFuture<Void> updateUser = CompletableFuture.runAsync(() ->userService.deleteUser(userData));
//        CompletableFuture<Void> updateCategory = CompletableFuture.runAsync(() ->categoryService.deleteUser(email));
//        CompletableFuture<Void> updateLanguage = CompletableFuture.runAsync(() -> languageService.deleteUser(email));
//        CompletableFuture.allOf(updateUser, updateCategory, updateLanguage).join();
        CompletableFuture.allOf(updateUser).join();
        log.info("deleteUser {} request  accessed!",userData.getUserID());
        return new ResponseEntity<>(userData.getUserID(),HttpStatus.OK);



    }


    @PutMapping("/api.updateUser")
    public ResponseEntity<?> updateUserName(@RequestBody UserIn user, @AuthenticationPrincipal Jwt jwt) {
        log.info("updateName request");

            userService.updateUserName(user,new UserData(jwt));
            log.info("updateName request ${user} accessed!");
            return new ResponseEntity<>(HttpStatus.OK);


    }


}

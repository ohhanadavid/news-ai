package com.user_accessor.user_accessor.BL;


import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.user_accessor.user_accessor.DAL.user.ChangePassword;
import com.user_accessor.user_accessor.DAL.user.LoginUser;
import com.user_accessor.user_accessor.DAL.user.UserOut;
import com.user_accessor.user_accessor.Exception.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user_accessor.user_accessor.DAL.user.User;
import com.user_accessor.user_accessor.Exception.ItemFoundException;
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

    @GetMapping("/api.getUserOut/{email}")
    public UserOut getUseOut(@PathVariable String email) {
        log.info("getUserOut request");
        UserOut user= userService.getUserOut(email);
        log.info("getUserOut request {} found!",user);
        if(user==null){
            throw new ItemNotFoundException("user not found");
        }
        return user;

    }

    @GetMapping("/api.getUser/{email}")
    public Optional<User> getUser(@PathVariable String email) {
        log.info("getUser request");
        try{
            Optional<User> user= userService.getUser(email);
            log.info("getUser request ${user} found!");
            if(user.isEmpty()){
                throw new ItemNotFoundException("user not found");
            }
            else{
                return user;
            }
        }catch( ItemNotFoundException  e){
            log.error("getUser request ItemNotFoundException {}",e.getMessage());
            throw e;
        }
        catch(Exception e){
            log.error("getUser request Exception {}",e.getMessage());
            throw e;

        }
    }

    @PostMapping("/api.login/{email}")
    public User loginUser(@PathVariable String email) {
        log.info("loginUser request");
        User user= userService.loginUser(email);
        log.info("loginUser request ${user} found!");
        if(user==null){
            throw new ItemNotFoundException("user not found");
        }
        return user;
    }

    @GetMapping("/api.userExists/{email}")
    public Boolean userExists(@PathVariable String email) {
        log.info("userExists ");
        return userService.userExists(email);

    }
                   
    @PostMapping("/api.createUser")
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        log.info("createUser request");


            User user=userService.creteUser(newUser);
            log.info("createUser request user accessed!");
            log.info(user);
            return new ResponseEntity<>(user,HttpStatus.OK);


    }

    @DeleteMapping("/api.deleteUser/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        log.info("deleteUser request");

            CompletableFuture<Void> updateUser = CompletableFuture.runAsync(() ->userService.deleteUser(email));
            CompletableFuture<Void> updateCategory = CompletableFuture.runAsync(() ->categoryService.deleteUser(email));
            CompletableFuture<Void> updateLanguage = CompletableFuture.runAsync(() -> languageService.deleteUser(email));
            CompletableFuture.allOf(updateUser, updateCategory, updateLanguage).join();
            log.info("deleteUser {} request  accessed!",email);
            return new ResponseEntity<>(email,HttpStatus.OK);



    }

    @PutMapping("/api.changePassword")
    public Boolean changePassword(@RequestBody ChangePassword data){
        return userService.changePassword(data.getUser(),data.getNewPassword());

    }

    @PutMapping("/api.updateUserName")
    public ResponseEntity<?> updateUserName(@RequestBody UserOut user) {
        log.info("updateName request");

            var res=userService.updateUserName(user);
            log.info("updateName request ${user} accessed!");
            return new ResponseEntity<>(res,HttpStatus.OK);


    }

    @PutMapping("/api.updateUserMail/{oldEmail}")
    public ResponseEntity<?> updateUserMail(@PathVariable String oldEmail,@RequestParam String newEmail) {
        log.info("updateMail request");
        CompletableFuture<Void> updateUser = CompletableFuture.runAsync(() ->userService.updateUserMail(oldEmail,newEmail));
        CompletableFuture<Void> updateCategory = CompletableFuture.runAsync(() ->categoryService.updateMail(oldEmail, newEmail));
        CompletableFuture<Void> updateLanguage = CompletableFuture.runAsync(() -> languageService.updateMail(oldEmail, newEmail));
        CompletableFuture.allOf(updateUser, updateCategory, updateLanguage).join();
        log.info("updateMail request  accessed!");
        return new ResponseEntity<>(String.format("%s update!!",newEmail),HttpStatus.OK);
    }


}

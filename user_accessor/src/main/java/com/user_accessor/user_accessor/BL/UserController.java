package com.user_accessor.user_accessor.BL;


import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    IUserKeyAction langugeService;

    @GetMapping("/api.getUser/{email}")
    public User getUser(@PathVariable String email) {
        log.info("getUser request");
        try{
            User user= userService.getUser(email);
            log.info("getUser request ${user} found!");
            if(user==null){
                throw new ItemNotFoundException("user not found");
            }
            else{
                return user;
            }
        }catch( ItemNotFoundException e){
            log.error("getUser request "+e.getMessage());
            throw e;
        }
        catch(Exception e){
            log.error("getUser request "+e.getMessage());
            throw e;

        }
    }
   
    @GetMapping("/api.userExists/{email}")
    public Boolean userExists(@PathVariable String email) {
        log.info("userExists ");
        try {

            Boolean b=userService.userExists(email);
            return b;
        } catch (Exception e) {
            log.error("userExists  "+e.getMessage());
            throw e;
        }
    }
                   
    @PostMapping("/api.createUser")
    public ResponseEntity<?> creatUser(@RequestBody(required = false)  User user){
        log.info("creatUser request");
        try{
            //Thread.sleep(10000);
            user=userService.creteUser(user);
            log.info("creatUser request user sccessed!");           
            log.info(user);           
            return new ResponseEntity<>(user,HttpStatus.OK);
            
        }
        catch (ItemFoundException e) {
            log.error("creatUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(400));
        }catch(Exception e)
        {
            log.error("creatUser request "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.resolve(500));

        }
    }
                    
    @PostMapping("/api.deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String,String> data){
        log.info("deleteUser request");
        try{
            String email=data.get("email");
            CompletableFuture<Void> updateUser = CompletableFuture.runAsync(() ->userService.deleteUser(email));
            CompletableFuture<Void> updateCategory = CompletableFuture.runAsync(() ->categoryService.deleteUser(email));
            CompletableFuture<Void> updateLanguege = CompletableFuture.runAsync(() ->langugeService.deleteUser(email));
            CompletableFuture.allOf(updateUser, updateCategory, updateLanguege).join();
            log.info("deleteUser request  sccessed!");           
            return new ResponseEntity<>(email,HttpStatus.OK);
            
        }
        catch (ItemNotFoundException e) {
            log.error("deleteUser request "+e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.resolve(400));
        }catch(Exception e)
        {
            log.error("deleteUser request "+e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.resolve(500));

        }
    }

    @PostMapping("/api.updateUserName")
    public ResponseEntity<?> updateUserName(@RequestBody User user) {
        log.info("updateName request");
        try{
            userService.updateUserName(user);
            log.info("updateName request ${user} sccessed!");           
            return new ResponseEntity<>(user,HttpStatus.OK);
            
        }
        catch (ItemNotFoundException e) {
            log.error("updateName request "+e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.resolve(400));
        }catch(Exception e)
        {
            log.error("updateName request "+e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.resolve(500));

        }
    }
                
    @PostMapping("/api.updateUserMail")
    public ResponseEntity<?> updateUserMail(@RequestBody Map<String,String> data) {
       
        log.info("updateMail request");
        try{
            String oldEmail=data.get("oldEmail");
            String newEmail=data.get("newEmail");
            CompletableFuture<Void> updateUser = CompletableFuture.runAsync(() ->userService.updateUserMail(oldEmail,newEmail));
            CompletableFuture<Void> updateCategory = CompletableFuture.runAsync(() ->categoryService.updateMail(oldEmail, newEmail));
            CompletableFuture<Void> updateLanguege = CompletableFuture.runAsync(() ->langugeService.updateMail(oldEmail, newEmail));
            CompletableFuture.allOf(updateUser, updateCategory, updateLanguege).join();
            log.info("updateMail request  sccessed!");           
            return new ResponseEntity<>(newEmail+" update!!",HttpStatus.OK);
            
        }
        catch (ItemNotFoundException | ItemFoundException e) {
            log.error("updateMail request "+e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.resolve(400));
        }catch(Exception e)
        {
            log.error("updateMail request "+e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.resolve(500));

        }
    }
    @GetMapping()
    public Boolean healthCheck(){
        return true;
    }

}

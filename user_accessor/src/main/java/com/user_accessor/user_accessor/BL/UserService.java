package com.user_accessor.user_accessor.BL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.user_accessor.user_accessor.DAL.user.User;
import com.user_accessor.user_accessor.DAL.user.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    public User creteUser(User user){
        log.info("func: createUser.  UserService");
        try {
            if(user.getName().equals(""))
                user.setName(user.getEmail());
             userRepository.save(user);
             log.info("user saved!");
        }
         catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return user;
    }

    public User getUser(String email){
        log.info("func: getUser.  UserService");
       User user;
        try {
             user =userRepository.findById(email).get();
             if(user==null)
                log.info("user not found!");
            else
                log.info("user found!");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return user;
    }

    @Async
    public void deleteUser(String email){
        log.info("func: deleteUser.  UserService");
        try {
             userRepository.deleteById(email);
             
            log.info("user deleted!");
           
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    public User updateUserName(User user){
        log.info("func: updateUserName.  UserService");
        
        try {
            userRepository.delete(user);            
            log.info("user deleted!");
             userRepository.save(user);
             log.info("user update!");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return user;
    }
    @Async
    public void updateUserMail(String oldeEmail,String newEmail){
        log.info("func: updateUserMail.  UserService");
        try {
             userRepository.updateEmail(newEmail, oldeEmail);
             log.info("user update!");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        
    }

    public boolean userExists(String email) {
        log.info("userExists func");
        return userRepository.existsById(email);
    }
  

 

}

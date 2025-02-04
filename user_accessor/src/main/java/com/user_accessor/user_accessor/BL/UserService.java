package com.user_accessor.user_accessor.BL;

import com.user_accessor.user_accessor.DAL.user.LoginUser;
import com.user_accessor.user_accessor.DAL.user.UserOut;
import com.user_accessor.user_accessor.Exception.ItemNotFoundException;
import com.user_accessor.user_accessor.Exception.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.user_accessor.user_accessor.DAL.user.User;
import com.user_accessor.user_accessor.DAL.user.UserRepository;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Log4j2
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    public User creteUser(User user){
        log.info("func: createUser.  UserService");

            if(user.getName().isEmpty())
                user.setName(user.getEmail());
            //  String password= getSHA256Hash(user.getPassword());
           // user.setPassword(password);
            userRepository.save(user);
            log.info("user saved!");

        return new User().setEmail(user.getEmail()).setName(user.getName());
    }

    public User loginUser(String email){
        log.info("func: loginUser.  UserService");

            Optional<User> dbUser = userRepository.findById(email);
            if(dbUser.isEmpty()){
                log.info("user {} no found!", email);
                throw new ItemNotFoundException("user "+"email"+" no found!");
            }
            log.info("user {} found!",email);
            return dbUser.get();
    }

    public UserOut getUserOut(String email){
        log.info("func: getUserOut. UserOut UserService");
       Optional<User> user;

             user =userRepository.findById(email);
             if(user.isEmpty()) {
                 log.info("getUserOut user not found!");
                 return null;
             }
            else
                log.info("user found!");


        return new UserOut(user.get());
    }

    public Optional<User> getUser(String email){
        log.info("func: getUser. UserOut UserService");
        Optional<User> user;

            user =userRepository.findById(email);
            if(user.isEmpty()) {
                log.info("user not found!");
                return Optional.empty();
            }
            return user;



    }

    @Async
    public void deleteUser(String email){
        log.info("func: deleteUser.  UserService");

             userRepository.deleteById(email);
             
            log.info("user deleted!");
           

    }

    public UserOut updateUserName(UserOut user){
        log.info("func: updateUserName.  UserService");
        

            userRepository.updateName(user.getEmail(), user.getName());
            log.info("func: updateUserName. user update!");


        return user;
    }

    @Async
    public void updateUserMail(String oldEmail,String newEmail){
        log.info("func: updateUserMail.  UserService");

             userRepository.updateEmail(newEmail, oldEmail);
             log.info("user update!");


        
    }

    public boolean userExists(String email) {
        log.info("userExists func");
        return userRepository.existsById(email);
    }

    public Boolean changePassword(LoginUser user,String password){
        log.info("func: changePassword.  UserService");
        userRepository.updatePassword(user.getEmail(), password);
        return true;


    }


}

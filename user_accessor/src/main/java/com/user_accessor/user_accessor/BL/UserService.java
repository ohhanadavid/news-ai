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
            userRepository.save(user);
            log.info("user saved!");

        return new User().setEmail(user.getEmail()).setName(user.getName());
    }

    public User loginUser(String email){
        log.info("func: loginUser.  UserService");

            Optional<User> dbUser = userRepository.findById(email);
            if(dbUser.isEmpty()){
                log.info("loginUser user {} no found!", email);
                throw new ItemNotFoundException("user "+"email"+" no found!");
            }
            log.info("loginUser user {} found!",email);
            return dbUser.get();
    }

    public UserOut getUserOut(String email){
        log.info("func: getUserOut. UserOut UserService for {}",email);
        Optional<User> user;
        user = userRepository.findById(email);
        if(user.isEmpty()) {
            log.info("getUserOut user {} not found!",email);
            return null;
        }
        log.info("getUserOut user {} found!",email);
        return new UserOut(user.get());
    }

    public Optional<User> getUser(String email){
        log.info("func: getUser. UserOut UserService for {}",email);
        Optional<User> user;
        user =userRepository.findById(email);
        if(user.isEmpty()) {
            log.info("getUser user {} not found!",email);
            return Optional.empty();
        }
        return user;
    }

    @Async
    public void deleteUser(String email){
        log.info("func: deleteUser.  UserService for {}",email);

             userRepository.deleteById(email);
             
            log.info("user {} deleted!",email);
           

    }

    public UserOut updateUserName(UserOut user){
        log.info("func: updateUserName.  UserService for {}",user.getEmail());
        

            userRepository.updateName(user.getEmail(), user.getName());
            log.info("func: updateUserName. user {} update!",user.getEmail());


        return user;
    }

    @Async
    public void updateUserMail(String oldEmail,String newEmail){
        log.info("func: updateUserMail.  UserService for {}",oldEmail);
        userRepository.updateEmail(newEmail, oldEmail);
        log.info("user {} update to {}!",oldEmail,newEmail);

    }

    public boolean userExists(String email) {
        log.info("userExists func for {}",email);
        return userRepository.existsById(email);
    }

    public Boolean changePassword(LoginUser user,String password){
        log.info("func: changePassword.  UserService for {}",user.getEmail());
        userRepository.updatePassword(user.getEmail(), password);
        return true;
    }


}

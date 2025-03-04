package com.user_accessor.user_accessor.BL;

import com.user_accessor.user_accessor.DAL.user.*;
import com.user_accessor.user_accessor.Exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Service
@Log4j2
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    public User creteUser(User user){
        log.info("func: createUser.  UserService for {}" ,user.getUserID());
            userRepository.save(new User(user.getUserID(), user.getPhone()));
            log.info("user {} saved!" ,user.getUserID());

        return user;
    }

    public UserOut getUserOut( UserData userData){
        log.info("func: getUserOut. UserOut UserService for {}",userData.getUserID());
        Optional<User> user;
        user = userRepository.findById(userData.getUserID());
        if(user.isEmpty()) {
            log.info("getUserOut user {} not found!",userData.getUserID());
            return null;
        }
        log.info("getUserOut user {} found!",userData.getUserID());
        return new UserOut(user.get());
    }

    @Async
    public void deleteUser(UserData userData){
        log.info("func: deleteUser.  UserService for {}",userData.getUserID());

             userRepository.deleteById(userData.getUserID());
             
            log.info("user {} deleted!",userData.getUserID());
           

    }

    public void updateUserName(UserIn user, UserData userData){
        log.info("func: updateUserName.  UserService for {}",userData.getUserID());
        

            userRepository.updateUser(userData.getUserID(), user.getPhone());
            log.info("func: updateUserName. user {} update!",userData.getUserID());



    }

}

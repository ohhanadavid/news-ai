package com.data_manager.data_manager.DAL.srevice;

import com.data_manager.data_manager.DAL.modol.user.UserToDB;
import com.data_manager.data_manager.DAL.repository.IUser;
import com.data_manager.data_manager.DAL.repository.UserRepository;
import com.data_manager.data_manager.DTO.user.UserOut;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserToDBService  {

    @Autowired
    private UserRepository userRepository ;

    public UserToDB creteUser(UserToDB user){
        log.info("func: createUser.  UserService for {}" ,user.getUserID());
            userRepository.save(new UserToDB(user.getUserID(), user.getPhone()));
            log.info("user {} saved!" ,user.getUserID());

        return user;
    }

    public UserOut getUserOut(String userID){
        log.info("func: getUserOut. UserOut UserService for {}",userID);
        Optional<UserOut> user;
        user = userRepository.getUser(userID);
        if(user.isEmpty()) {
            log.info("getUserOut user {} not found!",userID);
            return null;
        }
        log.info("getUserOut user {} found!",userID);
        return user.get();
    }


    public void deleteUser(String userID){
        log.info("func: deleteUser.  UserService for {}",userID);
        userRepository.deleteById(userID);
        log.info("user {} deleted!",userID);
           

    }

    public void updateUserPhone(String userID, String phone){
        log.info("func: updateUserName.  UserService for {}",userID);
        userRepository.updateUser(userID, phone);
        log.info("func: updateUserName. user {} update!",userID);



    }

}

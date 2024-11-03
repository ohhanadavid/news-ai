package com.user_accessor.user_accessor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user_accessor.user_accessor.BL.UserController;
import com.user_accessor.user_accessor.DAL.user.User;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class UserTest {
    // @Autowired
    // UserController userController;
    // User u1=new User("sustujbv167@gmail.com", "david");
    // User u2=new User("sustujbv168@gmail.com", "ori");
    // User u3=new User("sustujbv169@gmail.com", "shalom");
    // User u4=new User("sustujbv170@gmail.com", "moshe");
    // User u5=new User("sustujbv1788@gmail.com", "");
    // User u7=new User("nanaBanna", "");
    // @Test
    // public void creatUser( ) throws JsonProcessingException{
    //     log.info("creatUser request");
    //     // assertEquals(userController.creatUser(u1).getStatusCode(),HttpStatus.valueOf(200));
    //     // assertEquals(userController.creatUser(u2).getStatusCode(),HttpStatus.valueOf(200));
    //     // assertEquals(userController.creatUser(u3).getStatusCode(),HttpStatus.valueOf(200));
    //     // assertEquals(userController.creatUser(u4).getStatusCode(),HttpStatus.valueOf(200));
        
    //     User a=(User) userController.creatUser(u5).getBody();
    //     log.info("return result "+a);
    //     log.debug("return result "+a);
    //     //User u6 =objectMapper.readValue(a,User.class);
    //     assertEquals(a.getName(),a.getEmail());

       
        
    

    // }

    // @Test
    // public void getUser( ) {
    //     log.info("getUser request");
    //     Optional<User> user= (Optional<User>) userController.getUser(u2.getEmail()).getBody();
    //     assertEquals(user.get(), u2);
    //     assertEquals(userController.getUser("eee").getStatusCode(), HttpStatus.valueOf(400));
    // }
        




    // @Test
    // public void updateUserName() {
    //     log.info("updateName request");
    //     var res =userController.updateUserName(u3.setName("danone"));
    //     assertEquals(res.getStatusCode(), HttpStatus.OK);
    //     Optional<User> res2 =(Optional<User>) userController.getUser(u3.getEmail()).getBody();
    //     assertEquals(res2.get().getName(), "danone");
        
    //     assertEquals(userController.updateUserName(u7).getStatusCode(), HttpStatus.resolve(400));

    // }



    
    

}

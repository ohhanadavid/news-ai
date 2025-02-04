package com.data_manager.data_manager.DAL.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    private LoginUser user;
    private String newPassword;

    public User toJwtUser(){
        return new User(user.getEmail(),newPassword,new ArrayList<>());
    }

}

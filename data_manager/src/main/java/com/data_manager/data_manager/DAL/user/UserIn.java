package com.data_manager.data_manager.DAL.user;


import jakarta.validation.constraints.Email;

import lombok.*;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIn  {

    @Email
    private String email;
    private String name;
    private String password;


    public User toJwtUser(){
        return new User(email,password,new ArrayList<>());
    }

    public User toJwtUser(String password){
        return new User(email,password,new ArrayList<>());
    }
}

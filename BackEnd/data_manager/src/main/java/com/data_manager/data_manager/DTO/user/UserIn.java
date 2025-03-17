package com.data_manager.data_manager.DTO.user;


import jakarta.validation.constraints.Email;

import lombok.*;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIn  {

    private String userName;
    @Email
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;


    public User toJwtUser(){
        return new User(email,password,new ArrayList<>());
    }

    public User toJwtUser(String password){
        return new User(email,password,new ArrayList<>());
    }
}

package com.user_accessor.user_accessor.DAL.user;

import lombok.Data;
import org.springframework.security.oauth2.jwt.Jwt;

@Data
public class UserData {

    private final String userID;
    private final String email;
    private final String userName;


    public UserData(Jwt jwt){
        this.userID=jwt.getClaimAsString("sub");
        this.email=jwt.getClaimAsString("email");
        this.userName =jwt.getClaimAsString("preferred_username");


    }
}

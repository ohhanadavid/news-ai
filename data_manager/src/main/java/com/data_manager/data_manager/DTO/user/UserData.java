package com.data_manager.data_manager.DTO.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

@Data
public class UserData {

    private final String userID;
    private final String email;
    private final String userName;
    private final String token;

    public UserData (Jwt jwt){
        this.userID=jwt.getClaimAsString("sub");
        this.email=jwt.getClaimAsString("email");
        this.userName =jwt.getClaimAsString("preferred_username");
        this.token=jwt.getTokenValue();

    }
}

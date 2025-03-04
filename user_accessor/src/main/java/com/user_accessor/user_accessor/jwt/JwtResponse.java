package com.user_accessor.user_accessor.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.keycloak.representations.AccessTokenResponse;

import java.io.Serializable;


@AllArgsConstructor
@Data
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private final String refreshToken;


    public JwtResponse(AccessTokenResponse tokenResponse) {
        token=tokenResponse.getToken();
        refreshToken=tokenResponse.getRefreshToken();
    }
}

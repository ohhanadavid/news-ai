package com.data_manager.data_manager.jwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenRequest {

    String refreshToken;
    String clientId;
    String clientSecret;

    @Override
    public String toString() {
        StringBuilder str= new StringBuilder();
       return str.append("refresh_token=").append(refreshToken)
                .append("&grant_type=refresh_token")
                .append("&client_id=").append(clientId)
                .append("&client_secret=").append(clientSecret)
               .toString();

    }
}

package com.data_manager.data_manager.BL.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IChecking {
    public Boolean checkUser(String email);
    public <T> void checkResponse(ResponseEntity<?> response, Class<T> expectedType );

}

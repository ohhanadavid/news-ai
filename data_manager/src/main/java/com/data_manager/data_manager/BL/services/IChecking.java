package com.data_manager.data_manager.BL.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IChecking {
    public ResponseEntity<?> checkUser(String email);
    public <T> ResponseEntity<?> checkResponse(ResponseEntity<?> response,Class<T> expectedType );

}

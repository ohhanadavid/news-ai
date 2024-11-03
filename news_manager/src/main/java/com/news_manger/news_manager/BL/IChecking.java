package com.news_manger.news_manager.BL;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IChecking {
    public ResponseEntity<?> checkUser(String email);
    public <T> ResponseEntity<?> checkResponse(ResponseEntity<?> response,Class<T> expectedType );

}

package com.news_manger.news_manager.BL;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IChecking {
    public <T> void checkResponse(ResponseEntity<?> response, Class<T> expectedType );

}

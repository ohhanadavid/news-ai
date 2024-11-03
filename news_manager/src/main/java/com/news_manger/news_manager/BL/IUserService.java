package com.news_manger.news_manager.BL;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    public ResponseEntity<?> getUser( String email);
}

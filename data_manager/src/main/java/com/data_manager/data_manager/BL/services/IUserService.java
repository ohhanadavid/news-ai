package com.data_manager.data_manager.BL.services;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    public ResponseEntity<?> getUser( String email);
}

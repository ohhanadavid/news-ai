package com.data_manager.data_manager.BL.services;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ILanguageService {

    public ResponseEntity<?> getLanguegesCode(String email);
}

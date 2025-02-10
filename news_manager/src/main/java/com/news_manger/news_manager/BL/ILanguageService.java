package com.news_manger.news_manager.BL;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ILanguageService {

    public ResponseEntity<?> getLanguagesCode(String email);
}

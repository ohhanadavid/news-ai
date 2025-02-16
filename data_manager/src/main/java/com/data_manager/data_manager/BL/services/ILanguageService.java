package com.data_manager.data_manager.BL.services;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILanguageService {

    public List<String> getLanguegesCode(String email);
}

package com.data_manager.data_manager.BL.controller;

import com.data_manager.data_manager.DAL.languege.LanguageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.data_manager.data_manager.BL.services.LanguageService;
import com.data_manager.data_manager.DAL.languege.LanguageUser;
import com.data_manager.data_manager.DAL.languege.Language;
import com.data_manager.data_manager.DAL.languege.LanguageForChangeFromUser;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class LanguageController {
    @Autowired
    LanguageService languageService;
    
    @PostMapping("saveLanguage")
    public String saveLanguage(@RequestBody LanguageUser Language){
        log.info("Save Language");
        return languageService.saveLanguage(Language);

    }
    @GetMapping("getLanguages")
    public ResponseEntity<?> getLanguages(@RequestParam String email){
        log.info("get Languages");
        return languageService.getLanguages(email);

    }
    @GetMapping("getLanguagesCode")
    public ResponseEntity<?> getLanguagesCode(@RequestParam String email){
        log.info("get Languages code");
        return languageService.getLanguegesCode(email);

    }
    @DeleteMapping("deleteLanguage")
    public ResponseEntity<?> deleteLanguage(@RequestBody LanguageKey Language){
        log.info("delete language");
        return languageService.deleteLanguage(Language);

    }
    @PutMapping("updateLanguage/{email}")
    public ResponseEntity<?> updateLanguage(@RequestBody LanguageForChangeFromUser languages, @PathVariable String email){
        log.info("updateAll");
        return languageService.updateLanguage(languages,email);

    }
    


}

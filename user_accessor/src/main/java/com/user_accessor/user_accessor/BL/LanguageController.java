package com.user_accessor.user_accessor.BL;

import java.util.List;

import com.user_accessor.user_accessor.DAL.languege.LanguageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user_accessor.user_accessor.DAL.languege.Language;
import com.user_accessor.user_accessor.DAL.languege.LanguageForChange;
import com.user_accessor.user_accessor.Exception.ItemNotFoundException;

import lombok.extern.log4j.Log4j2;




@RestController
@Log4j2

public class LanguageController {
    @Autowired
    LanguageService languageService;
   
    @PostMapping("/api.saveLanguage")
    public String saveLanguage(@RequestBody Language language){
        log.info("Save language");
        languageService.saveLanguage(language);
        return "adding language secede!";


    }

    @GetMapping("/api.getLanguages/{email}")
    public List<String> getLanguages(@PathVariable String email){
        log.info("get languages");
        return languageService.getLanguages(email);

    }
   
    @GetMapping("/api.getLanguagesCode/{email}")
    public List<String> getLanguagesCode(@PathVariable String email){
        log.info("get languages code");
        return languageService.getLanguagesCode(email);

    }
    
    @DeleteMapping("/api.deleteLanguage")
    public ResponseEntity<?> deleteLanguage(@RequestBody LanguageKey language){
        log.info("delete language");
        languageService.deleteLanguage(language);
        String l = language.getLanguage();
        return new ResponseEntity<>(String.format("%s deleted!", l),HttpStatus.OK);

    }

    @PutMapping("/api.updateLanguage/{email}")
    public ResponseEntity<?> updateLanguage(@RequestBody LanguageForChange languages,@PathVariable String email){
        log.info("updateAll");
        languageService.updateLanguage(languages,email);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/api.numberOfMyLanguages/{email}")
    public Integer numberOfMyLanguages(@PathVariable String email) {
        log.info("api.numberOfMyLanguages");
        return languageService.numberOfMyLanguages(email);

    }
    
    
}



package com.data_manager.data_manager.BL.controller;

import com.data_manager.data_manager.DTO.user.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.data_manager.data_manager.BL.services.LanguageService;
import com.data_manager.data_manager.DTO.languege.LanguageUser;
import com.data_manager.data_manager.DTO.languege.LanguageForChangeFromUser;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@RestController("dataManager/userLanguages")
@Log4j2
public class LanguageController {
    @Autowired
    LanguageService languageService;
    
    @PostMapping("saveLanguage")
    public ResponseEntity<?> saveLanguage(@RequestBody LanguageUser Language, @AuthenticationPrincipal Jwt jwt){
        log.info("Save Language");
        languageService.saveLanguage(Language,new UserData(jwt));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("getMyLanguages")
    public List<String> getLanguages(@AuthenticationPrincipal Jwt jwt){
        log.info("get Languages");
        return languageService.getMyLanguages(new UserData(jwt));

    }

    @GetMapping("getMyLanguagesCode")
    public List<String> getLanguagesCode(@AuthenticationPrincipal Jwt jwt){
        log.info("get Languages code");
        return languageService.getLanguagesCode(new UserData(jwt));

    }

    @DeleteMapping("deleteLanguage")
    public String deleteLanguage(@RequestBody LanguageUser Language,@AuthenticationPrincipal Jwt jwt){
        log.info("delete language");
        return languageService.deleteLanguage(Language.getLanguage(),new UserData(jwt));

    }

    @PutMapping("updateLanguage")
    public String updateLanguage(@RequestBody LanguageForChangeFromUser languages,@AuthenticationPrincipal Jwt jwt){
        log.info("updateAll");
        return languageService.updateLanguage(languages,new UserData(jwt));

    }
    


}

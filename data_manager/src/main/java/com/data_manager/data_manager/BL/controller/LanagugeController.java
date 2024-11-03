package com.data_manager.data_manager.BL.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data_manager.data_manager.BL.services.LagaugeService;
import com.data_manager.data_manager.DAL.languege.LangaugeUser;
import com.data_manager.data_manager.DAL.languege.Language;
import com.data_manager.data_manager.DAL.languege.LanguageForChange;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class LanagugeController {
    @Autowired
    LagaugeService langugeService;
    
    @PostMapping("saveLanguege")
    public ResponseEntity<?> saveLanguege(@RequestBody LangaugeUser languege){
        log.info("Save languege");

        try{
             return langugeService.saveLanguege(languege);
        }
        catch(Exception e){
            log.error(e.getMessage());            
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   @GetMapping("getLangueges")
    public ResponseEntity<?> getLangueges(@RequestParam String email){    
        log.info("get langueges");
        try{
            
            return langugeService.getLangueges(email);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getLanguegesCode")
    public ResponseEntity<?> getLanguegesCode(@RequestParam String email){  
        log.info("get langueges code");
        try{
            
            return langugeService.getLanguegesCode(email);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("deleteLanguege")
    public ResponseEntity<?> deleteLanguege(@RequestBody Language languege){
        log.info("delete language");
        try {
            return langugeService.deleteLanguege(languege);

        }

         catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("updateLanguege")
    public ResponseEntity<?> updateLanguege(@RequestBody LanguageForChange languages){
        log.info("updateAll");
      
        try {
             
           return langugeService.updateLanguege(languages.getOldLanguage(),languages.getNewLanguage());
            
        }

         catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    


}

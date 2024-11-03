package com.user_accessor.user_accessor.BL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user_accessor.user_accessor.DAL.languege.Language;
import com.user_accessor.user_accessor.DAL.languege.LanguageForChange;
import com.user_accessor.user_accessor.Exception.ItemFoundException;
import com.user_accessor.user_accessor.Exception.ItemNotFoundException;

import lombok.extern.log4j.Log4j2;




@RestController
@Log4j2

public class LanguegeController {
    @Autowired
    LangugeService langugeService;
   
    @PostMapping("/api.saveLanguege")
    public ResponseEntity<?> saveLanguege(@RequestBody Language languege){
        log.info("Save languege");

        try{
             langugeService.saveLanguege(languege);
             return new ResponseEntity<>("adding language secced!", HttpStatus.OK);
        }catch(ItemFoundException |ItemNotFoundException e){
            log.error(e.getMessage());            
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            log.error(e.getMessage());            
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    @GetMapping("/api.getLangueges/{email}")
    public ResponseEntity<?> getLangueges(@PathVariable String email){    
        log.info("get langueges");
        try{
            
            return new ResponseEntity<>(langugeService.getLangueges(email),HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    @GetMapping("/api.getLanguegesCode/{email}")
    public List<String> getLanguegesCode(@PathVariable String email){  
        log.info("get langueges code");
        try{
            
            return langugeService.getLanguegesCode(email);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    
    @PostMapping("/api.deleteLanguege")
    public ResponseEntity<?> deleteLanguege(@RequestBody Language languege){
        log.info("delete language");
        try {
            langugeService.deleteLanguege(languege);
            String l = languege.getLanguageKey().getLanguage();
            return new ResponseEntity<>(String.format("%s deleted!", l),HttpStatus.OK);
        }catch(ItemNotFoundException e){
            log.error(e.getMessage());            
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
         catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/api.updateLanguege")
    public ResponseEntity<?> updateLanguege(@RequestBody LanguageForChange languages){
        log.info("updateAll");
      
        try {
             
            langugeService.updateLanguege(languages.getOldLanguage(),languages.getNewLanguage());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
         catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api.namberOfMyLanguages/{email}")
    public Integer namberOfMyLanguages(@PathVariable String email) {
        log.info("api.namberOfMyLanguages");
        try {

            return langugeService.namberOfMyLanguages(email);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    
    
}



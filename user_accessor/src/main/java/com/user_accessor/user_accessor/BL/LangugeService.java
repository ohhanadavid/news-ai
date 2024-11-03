package com.user_accessor.user_accessor.BL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.user_accessor.user_accessor.DAL.languege.Language;
import com.user_accessor.user_accessor.DAL.languege.LanguageRepository;
import com.user_accessor.user_accessor.Exception.ItemFoundException;
import com.user_accessor.user_accessor.Exception.ItemNotFoundException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@EnableAsync(proxyTargetClass=true)
public class LangugeService implements IUserKeyAction{
    @Autowired
    private LanguageRepository languegeRepository;
 

    public void saveLanguege(Language languege){
        log.info("Save languege");
        // userService.userNotExists(languege.getLanguageKey().getEmail());
        if(languegeRepository.existsById(languege.getLanguageKey())){
            log.error("languege alredy exisst!");
            throw new ItemFoundException("languege alredy exisst!");
        }
        try{
             languegeRepository.save(languege);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    public List<String> getLangueges(String email){    
        log.info("get langueges");
        try{
            return languegeRepository.getMyLanguages(email);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    public List<String> getLanguegesCode(String email){  
        log.info("get langueges code");
        try{
            return languegeRepository.getMyLanguagesCode(email);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    public void deleteLanguege(Language languege){
        log.info("delete language");
        if(!languegeRepository.existsById(languege.getLanguageKey()))
            throw new ItemNotFoundException("this language not exists");
        try {
            languegeRepository.deleteById(languege.getLanguageKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    @Async
    @Override
    public void deleteUser(String email){
        log.info("delete user");
      
        try {

            languegeRepository.deleteUser(email);
            log.info("deleted!");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    @Async
    @Override
    public void updateMail(String oldEmail,String newEmail){
        log.info("updateMail");
        
        try {
            
            languegeRepository.updateMail(oldEmail, newEmail);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    public void updateLanguege(Language oldLanguege,Language newLanguege){
        log.info("updateAll");
        if(!oldLanguege.getLanguageKey().getEmail().equals(oldLanguege.getLanguageKey().getEmail())){
            throw new IllegalArgumentException("email must be equals!");
        }
        try {

            languegeRepository.updateAll(oldLanguege.getLanguageKey().getEmail(),
                                        oldLanguege.getLanguageKey().getLanguage(),
                                        
                                        newLanguege.getLanguageKey().getLanguage(),
                                        newLanguege.getLanguageCode());

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
   public int namberOfMyLanguages(String email) {
        log.info("namberOfMyLanguages");
        try {
            int count = languegeRepository.namberOfMyLanguages(email);
            return count;
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
 
    
}

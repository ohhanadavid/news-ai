package com.user_accessor.user_accessor.BL;

import java.util.List;

import com.user_accessor.user_accessor.DAL.languege.LanguageForChange;
import com.user_accessor.user_accessor.DAL.languege.LanguageKey;
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
public class LanguageService implements IUserKeyAction{
    @Autowired
    private LanguageRepository languageRepository;
 

    public void saveLanguage(Language language){
        log.info("Save language for {}",language.getLanguageKey().getEmail());
        // userService.userNotExists(language.getLanguageKey().getEmail());
        if(languageRepository.existsById(language.getLanguageKey())){
            log.error("language already exist for {}!",language.getLanguageKey().getEmail());
            throw new ItemFoundException("language already exist!");
        }

             languageRepository.save(language);

    }
    public List<String> getLanguages(String email){
        log.info("get languages for {}",email);

            return languageRepository.getMyLanguages(email);

    }
    public List<String> getLanguagesCode(String email){
        log.info("get languages code for {}",email);

            return languageRepository.getMyLanguagesCode(email);

    }
    public void deleteLanguage(LanguageKey language){
        log.info("delete language for {}",language.getEmail());
        if(!languageRepository.existsById(language))
            throw new ItemNotFoundException("this language not exists for "+ language.getEmail());

        languageRepository.deleteById(language);

    }
    @Async
    @Override
    public void deleteUser(String email){
        log.info("delete user- delete language for {} ",email);

            languageRepository.deleteUser(email);
            log.info(" language deleted for {}!",email);


    }
    @Async
    @Override
    public void updateMail(String oldEmail,String newEmail){
        log.info("updateMail in languages for {}",oldEmail);
        languageRepository.updateMail(oldEmail, newEmail);

    }
    public void updateLanguage(LanguageForChange languages,String email){
        log.info("updateLanguage for {}",email);

        languageRepository.updateAll(email,
                    languages.getOldLanguage(),
                    languages.getNewLanguage(),
                    languages.getNewLanguageCode());


    }
   public int numberOfMyLanguages(String email) {
        log.info("numberOfMyLanguages for {}",email);

            return languageRepository.namberOfMyLanguages(email);

    }
 
    
}

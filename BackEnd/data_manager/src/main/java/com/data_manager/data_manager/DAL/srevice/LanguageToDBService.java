package com.data_manager.data_manager.DAL.srevice;


import com.data_manager.data_manager.DAL.modol.languege.LanguageToDB;
import com.data_manager.data_manager.DAL.modol.languege.LanguageForChange;
import com.data_manager.data_manager.DAL.modol.languege.LanguageKey;
import com.data_manager.data_manager.DAL.repository.IUser;
import com.data_manager.data_manager.DAL.repository.LanguageRepository;
import com.data_manager.data_manager.Exception.ItemFoundException;
import com.data_manager.data_manager.Exception.ItemNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@EnableAsync(proxyTargetClass=true)
public class LanguageToDBService implements IUser {
    @Autowired
    private LanguageRepository languageRepository;

    public void saveLanguage(LanguageToDB language){
        log.info("Save language for {}",language.getLanguageKey().getUserID());
        // userService.userNotExists(language.getLanguageKey().getEmail());
        if(languageRepository.existsById(language.getLanguageKey())){
            log.error("language already exist for {}!",language.getLanguageKey().getUserID());
            throw new ItemFoundException("language already exist!");
        }

        languageRepository.save(language);

    }

    public List<String> getLanguages(String userID){
        log.info("get languages for {}",userID);
        return languageRepository.getMyLanguages(userID);

    }

    public List<String> getLanguagesCode(String userID){
        log.info("get languages code for {}",userID);
        return languageRepository.getMyLanguagesCode(userID);

    }

    public void deleteLanguage(LanguageKey language){
        log.info("delete language for {}",language.getUserID());
        if(!languageRepository.existsById(language))
            throw new ItemNotFoundException("this language not exists for {}"+ language.getUserID());

        languageRepository.deleteById(language);

    }

    @Async
    @Override
    public void deleteUser(String userID){
        log.info("delete user- delete language for {} ",userID);

            languageRepository.deleteByUserID(userID);
            log.info(" language deleted for {}!",userID);


    }

    public void updateLanguage(LanguageForChange languages, String userID){
        log.info("updateLanguage for {}",userID);

        languageRepository.updateLanguage(userID,
                    languages.getOldLanguage(),
                    languages.getNewLanguage(),
                    languages.getNewLanguageCode());


    }

    public int numberOfMyLanguages(String userID) {
        log.info("numberOfMyLanguages for {}",userID);
        return languageRepository.countByUserID(userID);

    }
 
    
}

package com.data_manager.data_manager.BL.services;

import java.util.List;

import com.data_manager.data_manager.DAL.modol.languege.LanguageForChange;
import com.data_manager.data_manager.DAL.modol.languege.LanguageKey;
import com.data_manager.data_manager.DAL.modol.languege.LanguageToDB;
import com.data_manager.data_manager.DAL.srevice.LanguageToDBService;
import com.data_manager.data_manager.DTO.languege.*;
import com.data_manager.data_manager.DTO.user.UserData;
import com.data_manager.data_manager.Exception.ItemNotFoundException;
import com.data_manager.data_manager.Exception.MoreThenAllowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class LanguageService  {


    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LanguageToDBService languageToDBService;
    
    private final String  operation="create";


    public void saveLanguage(LanguageUser language, UserData userData){

        log.info("Save language for {}",userData.getUserID());


        int countOfMyLanguageResponse = languageToDBService.numberOfMyLanguages(userData.getUserID());

        UriComponents url = UriComponentsBuilder.fromHttpUrl(newsAiAccessorUrl).
                path("api.maximumLanguage").
                build();

        Integer MaximumLanguageResponse = restTemplate.getForObject(url.toUriString(), Integer.class);

        if(MaximumLanguageResponse==null){
            throw  new  HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(countOfMyLanguageResponse>=MaximumLanguageResponse)
            throw new MoreThenAllowException("languages",MaximumLanguageResponse);

        String codeResponse = getLanguageCodeResponse(language.getLanguage());

        if(codeResponse==null){
            throw  new  HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LanguageKey languageKey = new LanguageKey(userData.getUserID(),language.getLanguage());
        LanguageToDB languageToDB = new LanguageToDB(languageKey, codeResponse);
        languageToDBService.saveLanguage(languageToDB);

    }

    private String getLanguageCodeResponse(String language) {
        UriComponents url;
        url= UriComponentsBuilder.fromHttpUrl(newsAiAccessorUrl).
                path("api.checkLanguage/").
                path(language).
                build();

        if(Boolean.FALSE.equals(restTemplate.getForObject(url.toUriString(), Boolean.class))){
            throw new ItemNotFoundException("this Language not exist!");
        }

        url= UriComponentsBuilder.fromHttpUrl(newsAiAccessorUrl).
                path("api.getLanguageCode/").
                path(language).
                build();
        return restTemplate.getForObject(url.toUriString(), String.class);
    }

    public List<String> getMyLanguages(UserData userData){
        log.info("get languages for {}",userData.getUserID());

        return languageToDBService.getLanguages(userData.getUserID());
    }
    

    public List<String> getLanguagesCode(UserData userData){
        log.info("get languages code for {}",userData.getUserID());
       return  languageToDBService.getLanguagesCode(userData.getUserID());

    }
    
    public String deleteLanguage(String language,UserData userData){
        log.info("delete language for {}",userData.getUserID());

        languageToDBService.deleteLanguage(new LanguageKey(userData.getUserID(),language));

        return String.format("%s deleted", language);

    }

    public String updateLanguage(LanguageForChangeFromUser languages,UserData userData){
        log.info("updateAll language for {}",userData.getUserID());

            String codeResponse = getLanguageCodeResponse(languages.getNewLanguage());
            if(codeResponse==null){
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        LanguageForChange data = new LanguageForChange()
                    .setOldLanguage(languages.getOldLanguage())
                    .setNewLanguage(languages.getNewLanguage())
                    .setNewLanguageCode(codeResponse);
           languageToDBService.updateLanguage(data,userData.getUserID());
            return String.format("%s update", languages.getOldLanguage());

    }

 
}

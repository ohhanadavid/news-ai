package com.data_manager.data_manager.BL.services;

import java.util.List;

import com.data_manager.data_manager.DAL.languege.*;
import com.data_manager.data_manager.Exception.ItemNotFoundException;
import com.data_manager.data_manager.Exception.MoreThenAllowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class LanguageService implements ILanguageService {

    @Autowired
    private IChecking checking;
    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;
    @Autowired
    RestTemplate restTemplate;
    
    private final String  operation="create";


    public String saveLanguage(LanguageUser language){
        log.info("Save language for {}",language.getEmail());

//        Boolean check=checking.checkUser(language.getEmail());
//        if(!check)
//            throw new ItemNotFoundException("user  not found");
            

        UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                path("api.numberOfMyLanguages/").
                path(language.getEmail()).
                build();
        Integer countOfMyLanguageResponse = restTemplate.getForObject(url.toUriString(), Integer.class);
        if(countOfMyLanguageResponse==null){
            throw  new  HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        url= UriComponentsBuilder.fromHttpUrl(newsAiAccessorUrl).
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
            
        Language lToSend = new Language(language, codeResponse);
        url=UriComponentsBuilder.fromHttpUrl(userAccessorUrl).path("api.saveLanguage").build();
        return restTemplate.postForObject(url.toUriString(),lToSend,String.class);

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

    public List<String> getLanguages(String email){
        log.info("get languages for {}",email);
        List<String> response;
        UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                    path("api.getLanguages/").
                    path(email).
                    build();
        response = restTemplate.getForObject(url.toUriString(), List.class);

        if (response == null)
            throw new  HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);;
        return response;
    }
    
    @Override
    public List<String> getLanguegesCode(String email){
        log.info("get languages code for {}",email);
        UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                    path("api.getLanguagesCode/").
                    path(email).
                    build();
        List<String>  response = restTemplate.getForObject(url.toUriString(), List.class);
        if (response == null)
            throw new  HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);;
        return response;

    }
    
    public String deleteLanguage(LanguageKey language){
        log.info("delete language for {}",language.getEmail());

        UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.deleteLanguage").build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LanguageKey> requestEntity = new HttpEntity<>(language, headers);

        restTemplate.exchange(url.toUriString(), HttpMethod.DELETE,requestEntity,String.class);

        return String.format("%s deleted", language);

    }

    public String updateLanguage(LanguageForChangeFromUser languages, String email){
        log.info("updateAll language for {}",email);

            String codeResponse = getLanguageCodeResponse(languages.getNewLanguage());
            if(codeResponse==null){
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            LanguagesForChangeToSend data = new LanguagesForChangeToSend()
                    .setOldLanguage(languages.getOldLanguage())
                    .setNewLanguage(languages.getNewLanguage())
                    .setNewLanguageCode(codeResponse);
            UriComponents url=UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.updateLanguage/")
                    .path(email).build();
            restTemplate.put(url.toUriString(),data);
            return String.format("%s update", languages.getOldLanguage());

    }

 
}

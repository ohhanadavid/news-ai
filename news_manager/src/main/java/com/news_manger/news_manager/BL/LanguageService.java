package com.news_manger.news_manager.BL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class LanguageService implements ILanguageService {


   
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;
    @Autowired
    RestTemplate restTemplate;
    String url; 

  
    @Override
    public ResponseEntity<?> getLanguagesCode(String email){
        log.info("get languages code");

        url=String.format("%s/api.getLanguagesCode/%s",userAccessorUrl, email);
        List<String>  response = restTemplate.getForObject(url, List.class);
        if (response == null)
            return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    
   
 
}

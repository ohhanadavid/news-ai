package com.news_manger.news_manager.BL.servises;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpServerErrorException;
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
    public List<String> getLanguagesCode(){
        log.info("get languages code");

        url=String.format("%s/getMyLanguagesCode",userAccessorUrl);
        List<String>  response = restTemplate.getForObject(url, List.class);
        if (response == null)
            throw  new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }
    
   
 
}

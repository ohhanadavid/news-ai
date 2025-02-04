package com.data_manager.data_manager.BL.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class CheckingData implements IChecking {

    @Value("${UserAccessorUrl}")
    String userAccessorUrl;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public <T> void checkResponse(ResponseEntity<?> response, Class<T> expectedType ){
        log.info("checkData");
        if(response == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        if(!(expectedType.isInstance(response.getBody() )))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

     @Override
    public Boolean checkUser(String email){

         UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                 path("api.userExists/").
                 path(email).
                 build();
         return restTemplate.getForObject(url.toUriString(), Boolean.class);
       
    }
}

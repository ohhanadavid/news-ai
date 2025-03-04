package com.news_manger.news_manager.BL.servises;

import com.news_manger.news_manager.DAL.user.LoginUser;
import com.news_manger.news_manager.Exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.news_manger.news_manager.DAL.user.User;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class UserService implements IUserService {

    @Value("${UserAccessorUrl}")
    String userAccessorUrl;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public User getUser( String email) {
        log.info("getUser request");

            String url=String.format("%s/api.getUser/%s",userAccessorUrl, email);
            var response=restTemplate.getForObject( url, User.class);
            if(response == null){
                throw new ItemNotFoundException();
            }

            return response;

            

    }

    @Override
    public LoginUser logIn(String email) {
        log.info("logIn request");

        UriComponents url = UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                .path("api.login/")
                .path(email)
                .build();

        ResponseEntity<LoginUser> response = restTemplate.exchange(
                url.toUriString(),
                HttpMethod.POST,
                null,
                LoginUser.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new ItemNotFoundException("User not found");
        }
    }
    
  

}

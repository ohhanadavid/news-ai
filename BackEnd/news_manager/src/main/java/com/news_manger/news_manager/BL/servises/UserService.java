package com.news_manger.news_manager.BL.servises;


import com.news_manger.news_manager.Exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.news_manger.news_manager.DTO.user.User;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;


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

            String url=String.format("%s/getUser",userAccessorUrl);
            var response=restTemplate.getForObject( url, User.class);
            if(response == null){
                throw new ItemNotFoundException();
            }

            return response;

            

    }


}

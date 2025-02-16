package com.data_manager.data_manager.BL.services;

import java.util.Optional;

import com.data_manager.data_manager.DAL.user.ChangePassword;
import com.data_manager.data_manager.DAL.user.UserOut;
import com.data_manager.data_manager.Exception.ItemFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.data_manager.data_manager.DAL.user.UserIn;
import com.data_manager.data_manager.Exception.ItemNotFoundException;

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
    IChecking checking;
    @Autowired
    RestTemplate restTemplate;


    public UserOut getUserOut(String email) {
        log.info("getUserOut request");
        try{
            UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                    path("api.getUserOut/").
                    path(email).
                    build();
            UserOut response = restTemplate.getForObject(url.toUriString(), UserOut.class);
            if(response==null){
                throw new ItemNotFoundException();
            }

            return response;
        } catch(Exception e){
            log.error("getUserOut request {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<UserIn> getUser(String email) {
        log.info("getUser request");

        UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                    path("api.getUser/").
                    path(email).
                    build();
        Optional<UserIn> response = restTemplate.getForObject(url.toUriString(), Optional.class);

        if(response.isPresent())
            throw new ItemNotFoundException();

        return response;


    }

    @Override
    public UserIn saveUser(UserIn user){
        log.info("createUser request");

        Boolean check=checking.checkUser(user.getEmail());
        if(check){
            throw new ItemFoundException(String.format("%s already exists",user.getEmail()));
        }

            UriComponents url=UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.createUser")
                    .build();
            user= restTemplate.postForObject(url.toUriString(),user, UserIn.class);
            return user;

    }


    @Override
    public UserIn logIn(String email) {
        log.info("logIn request");

        UriComponents url = UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                .path("api.login/")
                .path(email)
                .build();

        ResponseEntity<UserIn> response = restTemplate.exchange(
                url.toUriString(),
                HttpMethod.POST,
                null,
                UserIn.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new ItemNotFoundException("User not found");
        }
    }

    public String deleteUser( String email){
        log.info("deleteUser request");

        userNotExistChecking(email);

        UriComponents url=UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.deleteUser/")
                    .path(email)
                    .build();
        restTemplate.delete(url.toUriString());
        return String.format("%S deleted!",email);

    }

    private void userNotExistChecking(String email) {
        Boolean check=checking.checkUser(email);
        if(!check )
            throw new ItemNotFoundException("user not found");
    }

    public String updateUserName( UserOut user) {
        log.info("updateName request");
        userNotExistChecking(user.getEmail());
        UriComponents url = UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.updateUserName")
                    .build();
        restTemplate.put(url.toUriString(),user);
        return "user name update!";

    }

    @Override
    public String updateUserMail(String oldEmail, String newEmail) {
        log.info("updateMail request");

            userNotExistChecking(oldEmail);

            Boolean check=checking.checkUser(newEmail);
            if(check){
                throw new ItemFoundException(String.format("%s already exists",newEmail));
            }

            UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.updateUserMail/")
                    .path(oldEmail)
                    .queryParam("newEmail", newEmail)
                    .build();
            restTemplate.put(url.toUriString(),null);
          
            return "mail update!";

    }

    @Override
    public Boolean changePassword(ChangePassword data){

        checking.checkUser(data.getUser().getEmail());

        UriComponents url=UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                .path("api.changePassword")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChangePassword> requestEntity = new HttpEntity<>(data, headers);
        var res=  restTemplate.exchange(url.toUriString(), HttpMethod.PUT,requestEntity,Boolean.class);

        checking.checkResponse(res,Boolean.class);
        return res.getBody();


    }
}

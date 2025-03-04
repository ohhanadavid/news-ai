package com.data_manager.data_manager.BL.services;

import java.util.*;

import com.data_manager.data_manager.DAL.modol.user.UserToDB;
import com.data_manager.data_manager.DAL.repository.IUser;
import com.data_manager.data_manager.DAL.srevice.UserToDBService;
import com.data_manager.data_manager.DTO.user.*;
import com.data_manager.data_manager.configuration.KeyCloakConfig;
import com.data_manager.data_manager.jwt.JwtResponse;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.annotation.RequestScope;


import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;


@Service
@Log4j2
@RequestScope
public class UserService  {

    @Autowired
    UserToDBService userToDBService;

    @Autowired
    @Qualifier("categoryToDBService")
    IUser category;

    @Autowired
    @Qualifier("languageToDBService")
    IUser language;

    public UserOut getUserOut(UserData user) {
        log.info("getUserOut request for {}",user.getUserID());
        return userToDBService.getUserOut(user.getUserID());
    }

    public JwtResponse saveUser(UserIn user){
        log.info("createUser request for {}",user.getEmail());
        RealmResource keycloak= getRealm();

        UserRepresentation userToSave = getUserToSave(user);

        Response response =keycloak.users().create(userToSave);

        if (response.getStatus() >= 500){
            throw new HttpServerErrorException(HttpStatus.valueOf(response.getStatus()));
        }
        else if (response.getStatus() >= 400){
            throw new HttpClientErrorException(HttpStatus.valueOf(response.getStatus()));
        }
        String location = response.getHeaderString("Location");
        String userID=location.substring(location.lastIndexOf("/") + 1);

        UserToDB userPhone= new UserToDB(userID,user.getPhone());
        userToDBService.creteUser(userPhone);
        log.info("user  {} saved",userID);

        return new JwtResponse(getAccessToken(new LoginUser(user.getUserName(),user.getPassword())));

    }

    public JwtResponse logIn(LoginUser user) {

        log.info("logIn request for {}",user.getUserIdentifier());

        AccessTokenResponse tokenResponse = getAccessToken(user);

        return new JwtResponse(tokenResponse);

    }

    public String deleteUser(UserData user){

        log.info("deleteUser request for {}",user.getUserID());

        category.deleteUser(user.getUserID());
        language.deleteUser(user.getUserID());
        userToDBService.deleteUser(user.getUserID());

        RealmResource keycloak= getRealm();
        Response response=keycloak.users().delete(user.getUserID());

        if (response.getStatus() >= 500){
            throw new HttpServerErrorException(HttpStatus.valueOf(response.getStatus()));
        }
        else if (response.getStatus() >= 400){
            throw new HttpClientErrorException(HttpStatus.valueOf(response.getStatus()));
        }



        return String.format("%S deleted!",user.getUserID());

    }

    public void updateUser(UserData userData, UserUpdate userUpdate) {

        log.info("update user  request for {}",userData.getUserID());
        RealmResource realmResource = getRealm();
        UserResource userResource = realmResource.users().get(userData.getUserID());

        UserRepresentation user = userResource.toRepresentation();

        userUpdate.getLastName().filter(x -> !x.isEmpty()).ifPresent(user::setLastName);
        userUpdate.getEmail().filter(x -> !x.isEmpty()).ifPresent(user::setEmail);
        userUpdate.getFirstName().filter(x -> !x.isEmpty()).ifPresent(user::setFirstName);
        userUpdate.getPhone().filter(x -> !x.isEmpty()).ifPresent(phone -> {
            userToDBService.updateUserPhone(userData.getUserID(), phone);
        });

        userResource.update(user);

    }

    public JwtResponse changePassword(ChangePassword data, UserData user){

        CredentialRepresentation password = new CredentialRepresentation();
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue(data.getNewPassword());
        password.setTemporary(false);

        getRealm().users().get(user.getUserID()).resetPassword(password);

        AccessTokenResponse tokenResponse = getAccessToken(new LoginUser(user.getUserName(), data.getNewPassword()));

        return new JwtResponse(tokenResponse);

    }

    private  AccessTokenResponse getImpersonatedToken(UserData userData) {
        return KeycloakBuilder.builder()
                .serverUrl(KeyCloakConfig.getServerUrl())
                .clientId(KeyCloakConfig.getClientId())
                .clientSecret(KeyCloakConfig.getClientSecret())
                .realm(KeyCloakConfig.getRealm())
                .username(userData.getUserName())
                .grantType(KeyCloakConfig.getClientGrantType())
                .build()
                .tokenManager()
                .grantToken();
    }

    private RealmResource getRealm() {
        return KeycloakBuilder.builder()
                .serverUrl(KeyCloakConfig.getServerUrl())
                .clientId(KeyCloakConfig.getClientId())
                .clientSecret(KeyCloakConfig.getClientSecret())
                .realm(KeyCloakConfig.getRealm())
                .grantType(KeyCloakConfig.getClientGrantType())
                .build()
                .realm(KeyCloakConfig.getRealm());
    }

    private  AccessTokenResponse getAccessToken(LoginUser user) {
        return KeycloakBuilder.builder()
                .serverUrl(KeyCloakConfig.getServerUrl())
                .clientId(KeyCloakConfig.getClientId())
                .clientSecret(KeyCloakConfig.getClientSecret())
                .realm(KeyCloakConfig.getRealm())
                .username(user.getUserIdentifier())
                .password(user.getPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build()
                .tokenManager()
                .getAccessToken();
    }

    private  UserRepresentation getUserToSave(UserIn user) {
        UserRepresentation userToSave = new UserRepresentation();
        userToSave.setUsername(user.getUserName());
        userToSave.setEmail(user.getEmail());
        userToSave.setFirstName(user.getFirstName());
        userToSave.setLastName(user.getLastName());

        userToSave.setEnabled(true);

        CredentialRepresentation password = new CredentialRepresentation();
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue(user.getPassword());
        password.setTemporary(false);
        userToSave.setCredentials(Collections.singletonList(password));
        return userToSave;
    }
}

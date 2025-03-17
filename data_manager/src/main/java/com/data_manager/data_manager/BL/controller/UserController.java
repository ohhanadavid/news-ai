package com.data_manager.data_manager.BL.controller;

import com.data_manager.data_manager.DTO.user.*;
import com.data_manager.data_manager.jwt.JwtResponse;
import com.data_manager.data_manager.jwt.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.data_manager.data_manager.BL.services.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController("dataManager/userData")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public JwtResponse createUser(@RequestBody UserIn userRequest) throws Exception {
        log.info("save user for {}",userRequest.getEmail());
        return userService.saveUser(userRequest);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> logInAndCreateAuthenticationToken(@RequestBody LoginUser authenticationRequest) throws Exception {
        log.info("authenticate for {}",authenticationRequest.getUserIdentifier());

        return ResponseEntity.ok(userService.logIn(authenticationRequest));

    }

    @GetMapping("getUser")
    public UserOut getUser(@AuthenticationPrincipal Jwt jwt) {
        log.info("getUser request");
        return userService.getUserOut(new UserData(jwt));
    }

    @PutMapping("updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdate userUpdate,@AuthenticationPrincipal Jwt jwt) {
        UserData data=new UserData(jwt);
        log.info("updateMail request for {}",data.getUserID());
        userService.updateUser(data, userUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword data,@AuthenticationPrincipal Jwt jwt) throws Exception {

        return ResponseEntity.ok( userService.changePassword(data,new UserData(jwt)));

    }


    @DeleteMapping("deleteUser")
    public String deleteUser(@AuthenticationPrincipal Jwt jwt){
        log.info("deleteUser request");

        return userService.deleteUser(new UserData(jwt));

    }

    @PostMapping("refreshToken")
    public JwtResponse jwtResponse (@RequestBody RefreshToken refreshToken,@AuthenticationPrincipal Jwt jwt){

        return userService.refreshToken(refreshToken,new UserData(jwt));
    }




}

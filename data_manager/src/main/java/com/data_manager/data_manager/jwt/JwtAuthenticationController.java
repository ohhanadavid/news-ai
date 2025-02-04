package com.data_manager.data_manager.jwt;

import com.data_manager.data_manager.BL.services.IUserService;
import com.data_manager.data_manager.DAL.user.ChangePassword;
import com.data_manager.data_manager.DAL.user.LoginUser;
import com.data_manager.data_manager.DAL.user.UserIn;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


import java.util.ArrayList;

@RestController
@CrossOrigin
@Log4j2
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager am;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> logInAndCreateAuthenticationToken(@RequestBody LoginUser authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        UserIn user=userService.logIn(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(user.toJwtUser());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserIn userRequest) throws Exception {
        String encodedPass = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encodedPass);
        UserIn user = userService.saveUser(userRequest);

        UserDetails userDetails = new User(user.getEmail(),encodedPass, new ArrayList<>());
        return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));
    }

    @PutMapping("updateMail/{oldEmail}")
    public ResponseEntity<?> updateUserMail(@PathVariable String oldEmail,@RequestParam String newEmail) {
        log.info("updateMail request");
        userService.updateUserMail(oldEmail, newEmail);
        UserIn user=userService.logIn(newEmail);
        final String token = jwtTokenUtil.generateToken(user.toJwtUser());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PutMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword data) throws Exception {
        log.info("changePassword request");
        authenticate(data.getUser().getEmail(),data.getUser().getPassword());
        String encodedPass = passwordEncoder.encode(data.getNewPassword());
        data.setNewPassword(encodedPass);
       if( userService.changePassword(data)) {

           final String token = jwtTokenUtil.generateToken(data.toJwtUser());
           return ResponseEntity.ok(new JwtResponse(token));
       }
       throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

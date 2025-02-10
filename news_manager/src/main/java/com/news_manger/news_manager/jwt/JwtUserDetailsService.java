package com.news_manger.news_manager.jwt;

import com.news_manger.news_manager.BL.IUserService;
import com.news_manger.news_manager.DAL.user.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        LoginUser dbUser = userService.logIn(email);
        if (dbUser!= null) {
            return new User(dbUser.getEmail(), dbUser.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(String.format("User not found : %s",email));
        }
    }
}

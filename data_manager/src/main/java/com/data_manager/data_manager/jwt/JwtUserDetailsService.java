package com.data_manager.data_manager.jwt;

import com.data_manager.data_manager.BL.services.IUserService;
import com.data_manager.data_manager.BL.services.UserService;
import com.data_manager.data_manager.DAL.user.UserIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserIn dbUser = userService.logIn(email);
        if (dbUser!= null) {
            return new User(dbUser.getEmail(), dbUser.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(STR."User not found : \{email}");
        }
    }
}

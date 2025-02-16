package com.data_manager.data_manager.BL.services;
import com.data_manager.data_manager.DAL.user.ChangePassword;
import com.data_manager.data_manager.DAL.user.LoginUser;
import com.data_manager.data_manager.DAL.user.UserIn;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public interface IUserService {

    public Optional<UserIn> getUser(String email);

    public UserIn saveUser(UserIn user);

    public UserIn logIn(String email);

    String updateUserMail(String oldEmail, String newEmail);

    Boolean changePassword(ChangePassword data);
}

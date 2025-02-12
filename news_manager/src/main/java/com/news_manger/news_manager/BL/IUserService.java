package com.news_manger.news_manager.BL;
import com.news_manger.news_manager.DAL.user.LoginUser;
import com.news_manger.news_manager.DAL.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    public User getUser(String email);

    LoginUser logIn(String email);
}

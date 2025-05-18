package com.news_manger.news_manager.BL.servises;
import com.news_manger.news_manager.DTO.user.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    public User getUser(String email);


}

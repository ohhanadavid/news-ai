package com.data_manager.data_manager.DAL.repository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


public interface IUser {


    @Async
    void deleteUser(String userID);
}

package com.user_accessor.user_accessor.BL;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync(proxyTargetClass=true)
public interface IUserKeyAction {
    @Async
    public void deleteUser(String email);
    @Async
    public void updateMail(String oldEmail,String newEmail);
}

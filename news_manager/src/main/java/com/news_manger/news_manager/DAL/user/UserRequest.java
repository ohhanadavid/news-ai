package com.news_manger.news_manager.DAL.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private  String userID;
    private Integer numberOfArticles;
    private String token;
    private SendOption option;
}

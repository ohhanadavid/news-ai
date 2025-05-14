package com.news_manger.news_manager.DTO.user;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;



@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
@Accessors(chain = true)

@Component("user")
@NoArgsConstructor
public class User {


    private String email;
   private String phone;
    private String name;
}

package com.newsdata.io_accessor.newsdata_io_accessor.DAL;


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
  
    private String name;
}

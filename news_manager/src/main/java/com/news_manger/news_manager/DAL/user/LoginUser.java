package com.news_manger.news_manager.DAL.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    @Email
    private String email;
    private String password;
}

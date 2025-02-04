package com.user_accessor.user_accessor.DAL.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
@Accessors(chain = true)
@SqlResultSetMapping(name = "LoginUser")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginUser {
    @Email
    private String email;
    private String password;
}

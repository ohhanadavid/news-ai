package com.data_manager.data_manager.DTO.user;

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
public class LoginUser implements Serializable {
    

    private String userIdentifier;
    private String password;
}

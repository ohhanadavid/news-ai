package com.user_accessor.user_accessor.DAL.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    private LoginUser user;
    private String newPassword;

}

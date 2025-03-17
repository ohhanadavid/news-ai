package com.data_manager.data_manager.DTO.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToSend {
    private String email;
    private String name;
    private String Password;
}

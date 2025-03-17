package com.data_manager.data_manager.DTO.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }



    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }

    public Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }
    public Optional<String> getLastName() {
        return Optional.ofNullable(firstName);
    }
}

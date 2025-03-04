package com.user_accessor.user_accessor.DAL.user;


import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;



@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name="users")
@Component
@NoArgsConstructor
public class User {


    @Id
    private String userID;
    private String phone;


}

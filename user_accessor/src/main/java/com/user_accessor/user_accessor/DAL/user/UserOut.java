package com.user_accessor.user_accessor.DAL.user;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Entity
@Table(name="users")
@Component
@NoArgsConstructor
@SqlResultSetMapping(name = "NewUser")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOut {


    @Id
    @Column(name= "email")
    @Email
    private String email;
    @Column(name= "name")
    private String name;

    public UserOut(User user){
        email= user.getEmail();
        name= user.getName();
    }

}
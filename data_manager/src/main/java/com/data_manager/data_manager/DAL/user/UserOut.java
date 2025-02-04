package com.data_manager.data_manager.DAL.user;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Component
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOut {

    @Email
    private String email;
    private String name;



}
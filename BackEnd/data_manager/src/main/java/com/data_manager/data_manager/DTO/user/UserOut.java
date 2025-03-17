package com.data_manager.data_manager.DTO.user;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;



//@Data
//@ToString(includeFieldNames = true)
//@AllArgsConstructor
//@Accessors(chain = true)
//@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public interface UserOut {

    String getName();
    String getEmail();
    String getPhone();

}
package com.data_manager.data_manager.DAL.modol.user;


import com.data_manager.data_manager.DTO.user.UserOut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;



@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name="users")
@Component
@NoArgsConstructor
public class UserToDB {


    @Id
    private String userID;
    private String phone;





}

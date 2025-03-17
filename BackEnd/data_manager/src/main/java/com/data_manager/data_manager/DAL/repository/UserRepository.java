package com.data_manager.data_manager.DAL.repository;

import com.data_manager.data_manager.DAL.modol.user.UserToDB;
import com.data_manager.data_manager.DTO.user.UserOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserToDB,String> {



    @Modifying
    @Transactional
    @Query(value= """
          UPDATE users
          SET phone= :phone
          WHERE userID= :userID;
          """,nativeQuery=true)
    public void updateUser(@Param("userID") String userId,@Param("phone")String phone);


    @Query(value= """
            SELECT concat(last_name,' ', first_name) AS name ,email AS email, phone AS phone
                      FROM  users
                      LEFT JOIN user_entity ON users.userID = user_entity.id
                      WHERE users.userID=:userIDToGet  ;
          """,nativeQuery=true)
    public Optional<UserOut> getUser(@Param("userIDToGet") String userIDToGet);
}

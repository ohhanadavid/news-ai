package com.user_accessor.user_accessor.DAL.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User,String> {



    @Modifying
    @Transactional
    @Query(value= """
          UPDATE users
          SET phone= :phone
          WHERE userID= :userID;
          """,nativeQuery=true)
    public void updateUser(@Param("userID") String userId,@Param("phone")String phone);
}

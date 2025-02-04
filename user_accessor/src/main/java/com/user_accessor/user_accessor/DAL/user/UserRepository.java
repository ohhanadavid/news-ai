package com.user_accessor.user_accessor.DAL.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User,String> {

    @Modifying
    @Transactional
    @Query(value="""
            UPDATE users
            SET email= :newEmail
            WHERE email= :oldEmail;
            """,nativeQuery=true)
    public void updateEmail(@Param("newEmail") String newEmail,@Param("oldEmail") String oldEmail);

    @Modifying
    @Transactional
    @Query(value="""
           SELECT u.email,u.password
           FROM users u
           WHERE u.email = :email
           """,nativeQuery=true)
    public LoginUser getLoginUser(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value= """
          UPDATE users
          SET password= :password
          WHERE email= :email;
          """,nativeQuery=true)
    public void updatePassword(@Param("email") String email,@Param("password")String password);

    @Modifying
    @Transactional
    @Query(value= """
          UPDATE users
          SET name= :name
          WHERE email= :email;
          """,nativeQuery=true)
    public void updateName(@Param("email") String email,@Param("name")String name);
}

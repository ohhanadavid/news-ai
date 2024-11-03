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
            UPDATE users u
            SET u.email= :newEmail
            WHERE u.email= :oldEmail;
            """,nativeQuery=true)
        public void updateEmail(@Param("newEmail") String newEmail,@Param("oldEmail") String oldEmail);

}

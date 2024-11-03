package com.user_accessor.user_accessor.DAL.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,CategoryKey> {


        @Modifying
        @Transactional
        @Query
        (value="""
                SELECT c.preferencec As preferencec
                FROM category c
                WHERE c.email= :email AND c.category= :category;
                """,nativeQuery=true)
        public List<String> getPreferencecByCategory(@Param("email") String email, @Param("category") String category);

        @Modifying
        @Transactional
        @Query
        (value="""
                SELECT c.category AS category,c.preferencec AS preferencec 
                FROM category c
                WHERE c.email= :email;
                """,nativeQuery=true)
        public List<Object[]> getMyPreferencec(@Param("email") String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                DELETE FROM category c
                WHERE c.email= :email AND c.category = :category;
                """,nativeQuery=true)
        public void deleteCategory(@Param("email") String email, @Param("category") String category);

        @Modifying
        @Transactional
        @Query
        (value="""
                DELETE FROM category c
                WHERE c.email= :email;
                """,nativeQuery=true)
        public void deleteUser(@Param("email") String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category c
                SET c.email= :newEmail
                WHERE c.email= :oldEmail;
                """,nativeQuery=true)
        public void updateMail(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category c
                SET c.category= :newCategory
                WHERE c.category= :oldCategory AND c.email = :email;
                """,nativeQuery=true)
        public void updateCategory(@Param("oldCategory") String oldCategory,@Param("newCategory") String newCategory,@Param("email")String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category c
                SET c.preferencec= :newPreferencec
                WHERE c.preferencec= :oldPreferencec AND c.email = :email AND c.category = :category;
                """,nativeQuery=true)
        public void updatePreferencec(@Param("oldPreferencec") String oldPreferencec,@Param("newPreferencec") String newPreferencec,@Param("email")String email,@Param("category") String category);

        @Modifying
        @Transactional
        @Query(value = """
        UPDATE category c
        SET     c.category = :newCategory,
                c.preferencec = :newPreferencec
        WHERE   c.email = :oldEmail AND
                c.category = :oldCategory AND
                c.preferencec = :oldPreferencec
        """, nativeQuery = true)
        public void updateAll(
        @Param("oldEmail") String oldEmail,
        @Param("oldCategory") String oldCategory,
        @Param("oldPreferencec") String oldPreferencec,
        @Param("newCategory") String newCategory,
        @Param("newPreferencec") String newPreferencec
        );


}

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
                SELECT c.preference As preference
                FROM category c
                WHERE c.email= :email AND c.category= :category;
                """,nativeQuery=true)
        public List<String> getPreferenceByCategory(@Param("email") String email, @Param("category") String category);

        @Modifying
        @Transactional
        @Query
        (value="""
                SELECT c.category AS category,c.preference AS preference
                FROM category c
                WHERE c.email= :email;
                """,nativeQuery=true)
        public List<Object[]> getMyPreference(@Param("email") String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                DELETE FROM category
                WHERE email= :email
                        AND category = :category;
                """,nativeQuery=true)
        public void deleteCategory(@Param("email") String email, @Param("category") String category);

        @Modifying
        @Transactional
        @Query
        (value="""
                DELETE FROM category
                WHERE email= :email;
                """,nativeQuery=true)
        public void deleteUser(@Param("email") String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category
                SET email= :newEmail
                WHERE email= :oldEmail;
                """,nativeQuery=true)
        public void updateMail(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category
                SET category= :newCategory
                WHERE category= :oldCategory
                        AND email = :myEmail;
                """,nativeQuery=true)
        public void updateCategory(@Param("oldCategory") String oldCategory,@Param("newCategory") String newCategory,@Param("myEmail")String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category
                SET preference= :newPreference
                WHERE preference= :oldPreference
                        AND email = :email
                        AND category = :category;
                """,nativeQuery=true)
        public void updatePreference(@Param("oldPreference") String oldPreference,@Param("newPreference") String newPreference,@Param("email")String email,@Param("category") String category);

        @Modifying
        @Transactional
        @Query(value = """
        UPDATE category
        SET     category = :newCategory,
                preference = :newPreference
        WHERE   email = :oldEmail AND
                category = :oldCategory AND
                preference = :oldPreference
        """, nativeQuery = true)
        public void updateAll(
        @Param("oldEmail") String oldEmail,
        @Param("oldCategory") String oldCategory,
        @Param("oldPreference") String oldPreference,
        @Param("newCategory") String newCategory,
        @Param("newPreference") String newPreference
        );


}

package com.user_accessor.user_accessor.DAL.languege;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, LanguageKey> {

            @Modifying
        @Transactional
        @Query
        (value="""
                SELECT l.language
                FROM language l
                WHERE l.email= :email;
                """,nativeQuery=true)
        public List<String> getMyLanguages(@Param("email") String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                SELECT l.language_code 
                FROM language l
                WHERE l.email= :email;
                """,nativeQuery=true)
        public List<String> getMyLanguagesCode(@Param("email") String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                DELETE FROM language
                WHERE email= :email;
                """,nativeQuery=true)
        public void deleteUser(@Param("email") String email);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE language
                SET email= :newEmail
                WHERE email= :oldEmail;
                """,nativeQuery=true)
        public void updateMail(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail);

        @Modifying
        @Transactional
        @Query(value = """
                UPDATE language
                SET     language = :newLanguage,
                        language_code = :newLanguageCode
                WHERE   email = :oldEmail AND
                        language = :oldLanguage
                """, nativeQuery = true)
        public void updateAll(
        @Param("oldEmail") String oldEmail,
        @Param("oldLanguage") String oldLanguage,
        @Param("newLanguage") String newLanguage,
        @Param("newLanguageCode") String newLanguageCode
        );


        @Query(value = """
                SELECT count(l.email)
                FROM language l
                WHERE l.email = :email;
        """, nativeQuery = true)
        public int namberOfMyLanguages(@Param("email") String email);
}

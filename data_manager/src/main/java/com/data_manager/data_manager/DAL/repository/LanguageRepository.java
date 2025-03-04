package com.data_manager.data_manager.DAL.repository;

import com.data_manager.data_manager.DAL.modol.languege.LanguageToDB;
import com.data_manager.data_manager.DAL.modol.languege.LanguageKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageToDB, LanguageKey> {


        @Query
        (value="""
                SELECT l.language
                FROM language l
                WHERE l.userID= :userID;
                """,nativeQuery=true)
        public List<String> getMyLanguages(@Param("userID") String userID);


        @Query
                (value="""
                SELECT l.language_code
                FROM language l
                WHERE l.userID= :userID;
                """,nativeQuery=true)
        public List<String> getMyLanguagesCode(@Param("userID") String userID);


        @Modifying
        @Transactional
        @Query
                (value="""
                DELETE FROM language
                WHERE userID= :userID;
                """,nativeQuery=true)
        public void deleteByUserID(@Param("userID") String userID);
     

        @Modifying
        @Transactional
        @Query(value = """
                UPDATE language
                SET     language = :newLanguage,
                        language_code = :newLanguageCode
                WHERE   userID = :userID AND
                        language = :oldLanguage
                """, nativeQuery = true)
        public void updateLanguage(
        @Param("userID") String userID,
        @Param("oldLanguage") String oldLanguage,
        @Param("newLanguage") String newLanguage,
        @Param("newLanguageCode") String newLanguageCode
        );


        @Query(value = """
                SELECT count(l.userID)
                FROM language l
                WHERE l.userID = :userID;
        """, nativeQuery = true)
        public int countByUserID(@Param("userID") String userID);
}

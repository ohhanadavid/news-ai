package com.data_manager.data_manager.DAL.repository;

import com.data_manager.data_manager.DAL.modol.category.CategoryToDB;
import com.data_manager.data_manager.DAL.modol.category.CategoryKeyForDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryToDB, CategoryKeyForDB> {



        @Query
        (value="""
                SELECT c.preference As preference
                FROM category c
                WHERE c.userID= :userID AND c.category= :category;
                """,nativeQuery=true)
        public List<String> getPreferenceByCategory(@Param("userID") String userID, @Param("category") String category);


        @Query
        (value="""
                SELECT c.category AS category,c.preference AS preference
                FROM category c
                WHERE c.userID= :userID;
                """,nativeQuery=true)
        public List<Object[]> getMyPreference(@Param("userID") String userID);

        @Modifying
        @Transactional
        @Query
        (value="""
                DELETE FROM category
                WHERE userID= :userID
                        AND category = :category;
                """,nativeQuery=true)
        public void deleteByCategoryAndUserID(@Param("userID") String userID, @Param("category") String category);


        @Modifying
        @Transactional
        @Query
                (value="""
                DELETE FROM category
                WHERE userID= :userID;
                """,nativeQuery=true)
        public void deleteByUserID(@Param("userID") String userID);


        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category
                SET category= :newCategory
                WHERE category= :oldCategory
                        AND userID = :userID;
                """,nativeQuery=true)
        public void updateCategory(@Param("oldCategory") String oldCategory,@Param("newCategory") String newCategory,@Param("userID")String userID);

        @Modifying
        @Transactional
        @Query
        (value="""
                UPDATE category
                SET preference= :newPreference
                WHERE preference= :oldPreference
                        AND userID = :userID
                        AND category = :category;
                """,nativeQuery=true)
        public void updatePreference(@Param("oldPreference") String oldPreference,@Param("newPreference") String newPreference,@Param("userID")String userID,@Param("category") String category);



        boolean existsByCategory_UserIDAndCategory(String userID, String category);




}

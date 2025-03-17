package com.data_manager.data_manager.DAL.srevice;


import com.data_manager.data_manager.DAL.modol.category.CategoryToDB;
import com.data_manager.data_manager.DAL.modol.category.CategoryKeyForDB;
import com.data_manager.data_manager.DAL.repository.CategoryRepository;
import com.data_manager.data_manager.DAL.repository.IUser;
import com.data_manager.data_manager.DTO.category.PreferenceForChange;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CategoryToDBService implements IUser {
    @Autowired
    CategoryRepository categoryRepository;


    public CategoryToDB saveCategory (CategoryToDB category){
        log.info("Save Category for {}",category.getCategory().getUserID());
        return categoryRepository.save(category);

    }

    public List<String> getPreferencesByCategory(String userID, String category){
        log.info("getPreferenceByCategory for {}",userID);
        return categoryRepository.getPreferenceByCategory(userID, category);

    }

    public Map<String,List<String>> myCategories (String userID){
        log.info("get Category for {} ",userID);
        List<Object[]> c= categoryRepository.getMyPreference(userID);
        log.info("get data for {}",userID);
        return c.stream().collect(Collectors.groupingBy(
                result -> (String) result[0],
                Collectors.mapping(
                        result -> (String) result[1],
                        Collectors.toList()
                )
        ));

    }

    public void deletePreferences(CategoryToDB category){
        log.info("deletePreferences: delete Preference {} ",category);
        categoryRepository.delete(category);
        log.info("deletePreferences deleted!");
    }

    @Async
    @Override
    public void deleteUser(String userID){
        log.info("delete user");
        categoryRepository.deleteByUserID(userID);
        log.info("deleted!");


    }

    public void deleteCategory (String userID,String category){
        log.info("delete Preference");
        categoryRepository.deleteByCategoryAndUserID(userID, category);


    }

    public void updateCategory( String oldCategory, String newCategory,String userID){
        log.info("updateCategory");
        categoryRepository.updateCategory(oldCategory, newCategory,userID);


    }

    public void updatePreference(PreferenceForChange preferences, String userID){
        log.info("updatePreference");
        categoryRepository.updatePreference(preferences.getOldPreference(), preferences.getNewPreference(),userID,preferences.getCategory());


    }

    public Boolean checkCategory(String userId,String category){
        return categoryRepository.existsByCategory_UserIDAndCategory(userId, category);
    }



    public boolean checkPreference(@RequestBody CategoryKeyForDB category) {

            return categoryRepository.existsById(category);

    }

}

package com.user_accessor.user_accessor.BL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.user_accessor.user_accessor.DAL.category.CategoryForChangingAll;
import com.user_accessor.user_accessor.DAL.category.PreferenceForChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.user_accessor.user_accessor.DAL.category.Category;
import com.user_accessor.user_accessor.DAL.category.CategoryRepository;


import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CategoryService implements IUserKeyAction {
    @Autowired
    CategoryRepository categoryRepository;


    public Category saveCategory (Category category){
        log.info("Save Category");
        return categoryRepository.save(category);

    }
    public List<String> getPreferencesByCategory(String email, String category){
        log.info("getPreferenceByCategory");

            
            return categoryRepository.getPreferenceByCategory(email, category);

    }
    public Map<String,List<String>> myCategories (String email){
        log.info("get Category");

            List<Object[]> c= categoryRepository.getMyPreference(email);
            log.info("get data");
            return c.stream().collect(Collectors.groupingBy(
                result -> (String) result[0],
                Collectors.mapping(
                        result -> (String) result[1],
                        Collectors.toList()
                )
        ));

    }

    public void deletePreferences(Category category){
        log.info("deletePreferences: delete Preference");
       


            categoryRepository.delete(category);
            log.info("deletePreferences deleted!");


    }
    @Async
    @Override
    public void deleteUser (String email){
        log.info("delete user");
      

            categoryRepository.deleteUser(email);
            log.info("deleted!");


    }

    public void deleteCategory (String email,String category){
        log.info("delete Preference");
        

            categoryRepository.deleteCategory(email, category);


    }

    public void updateCategory( String oldCategory, String newCategory,String email){
        log.info("updateCategory");
        categoryRepository.updateCategory(oldCategory, newCategory,email);


    }
    public void updatePreference(PreferenceForChange preferences, String email){
        log.info("updatePreference");
        

            categoryRepository.updatePreference(preferences.getOldPreference(), preferences.getNewPreference(),email,preferences.getCategory());


    }
    public void updateAll( CategoryForChangingAll categories){
        log.info("updateAll");
       

            categoryRepository.updateAll(categories.getEmail(),
                                        categories.getOldCategory() ,
                                        categories.getOldPreference(),
                                        categories.getNewCategory(),
                                        categories.getNewPreference());


    }
    @Async
    @Override
    public void updateMail(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail){
        log.info("updateMail");
        

            categoryRepository.updateMail(oldEmail, newEmail);


    }

    public boolean checkPreference(@RequestBody Category category) {

            return categoryRepository.existsById(category.getCategory());                    

    }

}

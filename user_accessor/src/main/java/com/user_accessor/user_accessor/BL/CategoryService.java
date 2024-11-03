package com.user_accessor.user_accessor.BL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        // userService.userNotExists(category.getCategory().getEmail());
        
        try{
            return categoryRepository.save(category);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    public List<String> getPreferencecByCategory (String email,String category){
        log.info("getPreferencecByCategory");
        try{  
            
            return categoryRepository.getPreferencecByCategory(email, category);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    public Map<String,List<String>> myCategories (String email){
        log.info("get Category");
        try{
            List<Object[]> c= categoryRepository.getMyPreferencec(email);
            log.info("get data");
            Map<String,List<String>> categoryResult= c.stream().collect(Collectors.groupingBy(
                result -> (String) result[0],
                Collectors.mapping(
                        result -> (String) result[1],  
                        Collectors.toList()            
                )
        ));
            return categoryResult;
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deletePreferencec (Category category){
        log.info("delete Preferencec");
       
        try {

            categoryRepository.delete(category);
            log.info("deleted!");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    @Async
    @Override
    public void deleteUser (String email){
        log.info("delete user");
      
        try {

            categoryRepository.deleteUser(email);
            log.info("deleted!");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteCategory (String email,String category){
        log.info("delete Preferencec");
        
        try {

            categoryRepository.deleteCategory(email, category);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void updateCategory( String oldCategory, String newCategory,String email){
        log.info("updateCategory");
        
        try {

            categoryRepository.updateCategory(oldCategory, newCategory,email);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    public void updatePreferencec( String oldPreferencec, String newPreferencec,String email,String category){
        log.info("updatePreferencec");
        
        try {

            categoryRepository.updatePreferencec(oldPreferencec, newPreferencec,email,category);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    public void updateAll( Category oldCategory, Category newCategory){
        log.info("updateAll");
       
        try {

            categoryRepository.updateAll(oldCategory.getCategory().getEmail(),
                                        oldCategory.getCategory().getCategory(),
                                        oldCategory.getCategory().getPreferencec(),
                                        newCategory.getCategory().getCategory(),
                                        newCategory.getCategory().getPreferencec());

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    @Async
    @Override
    public void updateMail(@Param("oldEmail") String oldEmail,@Param("newEmail") String newEmail){
        log.info("updateMail");
        
        try {
            
            categoryRepository.updateMail(oldEmail, newEmail);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean checkPreferencec(@RequestBody Category category) {
        try{
            return categoryRepository.existsById(category.getCategory());                    
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

}

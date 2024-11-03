package com.data_manager.data_manager.BL.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data_manager.data_manager.BL.services.CategoryService;
import com.data_manager.data_manager.DAL.category.Category;
import com.data_manager.data_manager.DAL.category.CategoryForChange;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("saveCategory")
    public ResponseEntity<?> saveCategory (@RequestBody Category category){
        log.info("Save Category");
        
        try{
            return categoryService.saveCategory(category);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getPreferencecByCategory")
    public ResponseEntity<?> getPreferencecByCategory (@RequestParam String email,@RequestParam String category){
        log.info("getPreferencecByCategory");
        try{  
            
            return categoryService.getPreferencecByCategory(email, category);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("myCategories")
    public ResponseEntity<?> myCategories (@RequestParam String email){
        log.info("get Category");
        try{
            return categoryService.myCategories(email);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("deletePreferencec")
    public ResponseEntity<?> deletePreferencec (@RequestBody Category category){
        log.info("delete Preferencec");
      
        try {
            return categoryService.deletePreferencec(category);
            
        } 
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteCategory")
    public ResponseEntity<?> deleteCategory (@RequestParam String email,@RequestParam String category){
        log.info("delete Preferencec");
        
        try {

            return categoryService.deleteCategory(email, category);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("updateCategory")
    public ResponseEntity<?> updateCategory(@RequestParam String oldCategory,@RequestParam String newCategory,@RequestParam String email){
        log.info("updateCategory");
        
        try {
            return categoryService.updateCategory(oldCategory, newCategory, email);
            
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("updatePreferencec")
    public ResponseEntity<?> updatePreferencec(@RequestParam String oldPreferencec,@RequestParam String newPreferencec,@RequestParam String email,@RequestParam String category){
        log.info("updatePreferencec");
        
        try {

            return categoryService.updatePreferencec(oldPreferencec, newPreferencec, email, category);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("updateAll")
    public ResponseEntity<?> updateAll( @RequestBody CategoryForChange categories){
        log.info("updateAll");
          
        try {

            return categoryService.updateAll(categories);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.user_accessor.user_accessor.BL;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user_accessor.user_accessor.DAL.category.Category;
import com.user_accessor.user_accessor.DAL.category.CategoryForChange;
import com.user_accessor.user_accessor.DAL.category.CategoryKey;
import com.user_accessor.user_accessor.Exception.ItemFoundException;
import com.user_accessor.user_accessor.Exception.ItemNotFoundException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/api.saveCategory")
    public ResponseEntity<?> saveCategory (@RequestBody Category category){
        log.info("Save Category");
        
        try{
            return new ResponseEntity<>( categoryService.saveCategory(category),HttpStatus.OK);
        }catch(ItemFoundException |ItemNotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/api.getPreferencecByCategory/{email}/{category}")
    public List<String> getPreferencecByCategory (@PathVariable String email,@PathVariable String category){
        log.info("getPreferencecByCategory");
        try{  
          
            return categoryService.getPreferencecByCategory(email, category);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    
    @GetMapping("/api.myCategories/{email}")
    public Map<String,List<String>> myCategories (@PathVariable String email){
        log.info("get Category");
        try{
            
            
            Map<String,List<String>> categoryResult= categoryService.myCategories(email);
      
            return categoryResult;
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    
    @PostMapping("/api.deletePreferencec")
    public ResponseEntity<?> deletePreferencec (@RequestBody Category category){
        log.info("delete Preferencec");
      
        try {

            categoryService.deletePreferencec(category);
            log.info("deleted!");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ItemNotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api.deleteCategory")
    public ResponseEntity<?> deleteCategory (@RequestBody Map<String,String> data){
        log.info("delete Preferencec");
        
        try {
            String email=data.get("email");
            String category=data.get("category");
            categoryService.deleteCategory(email, category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/api.updateCategory")
    public ResponseEntity<?> updateCategory(@RequestBody Map<String,String> data){
        log.info("updateCategory");
        
        try {
            String oldCategory=data.get("oldCategory"); 
            String newCategory=data.get("newCategory"); 
             String email=data.get("email"); 
            categoryService.updateCategory(oldCategory, newCategory,email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/api.updatePreferencec")
    public ResponseEntity<?> updatePreferencec(@RequestBody Map<String,String> data){
        log.info("updatePreferencec");
        
        try {
            String oldPreferencec=data.get("oldPreferencec");
            String newPreferencec=data.get("newPreferencec");
            String email=data.get("email");
            String category=data.get("category");
            categoryService.updatePreferencec(oldPreferencec, newPreferencec,email,category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/api.updateAll")
    public ResponseEntity<?> updateAll( @RequestBody CategoryForChange categories){
        log.info("updateAll");
          
        try {

            categoryService.updateAll(categories.getOldCategory(), categories.getNewCategory());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IllegalArgumentException e ){
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>( e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api.checkPreferencec/{email}/{preferencec}/{category}")
    public Boolean checkPreferencec(@PathVariable String email,@PathVariable String preferencec,@PathVariable String category) {
        try {
            Category c = new Category().setCategory(new CategoryKey(email,preferencec, category));
            boolean exists=categoryService.checkPreferencec(c);
            
            return exists;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    



}

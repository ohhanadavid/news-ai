package com.user_accessor.user_accessor.BL;

import java.util.List;
import java.util.Map;

import com.user_accessor.user_accessor.DAL.category.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user_accessor.user_accessor.Exception.ItemNotFoundException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/api.saveCategory")
    public Category saveCategory (@Valid @RequestBody Category category){
        log.info("Save Category");
        return categoryService.saveCategory(category);

    }

    @GetMapping("/api.getPreferenceByCategory/{email}/{category}")
    public List<String> getPreferenceByCategory (@PathVariable String email,@PathVariable String category){
        log.info("getPreferenceByCategory");

            return categoryService.getPreferencesByCategory(email, category);

    }
    
    @GetMapping("/api.myCategories/{email}")
    public Map<String,List<String>> myCategories (@PathVariable String email){
        log.info("get Category");

            return categoryService.myCategories(email);

    }
    
    @DeleteMapping("/api.deletePreference")
    public void deletePreference (@RequestBody Category category){
        log.info("delete Preference");
        categoryService.deletePreferences(category);
        log.info("deleted!");


    }

    @DeleteMapping("/api.deleteCategory/{email}")
    public ResponseEntity<?> deleteCategory (@PathVariable String email,@RequestParam String category){
        log.info("delete Category");

        categoryService.deleteCategory(email, category);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/api.updateCategory/{email}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryForChange category, @PathVariable String email){
        log.info("updateCategory");

            String oldCategory=category.getOldCategory();
            String newCategory=category.getNewCategory();
            categoryService.updateCategory(oldCategory, newCategory,email);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/api.updatePreference/{email}")
    public ResponseEntity<?> updatePreference(@RequestBody PreferenceForChange data, @PathVariable String email){
        log.info("updatePreference");


            categoryService.updatePreference(data,email);
            return new ResponseEntity<>(HttpStatus.OK);


    }

    @PutMapping("/api.updateAll")
    public ResponseEntity<?> updateAll( @RequestBody CategoryForChangingAll categories){
        log.info("updateAll");

            categoryService.updateAll(categories);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/api.checkPreference/{email}")
    public Boolean checkPreference(@PathVariable String email,@RequestParam String preference,@RequestParam String category) {

        Category c = new Category().setCategory(new CategoryKey(email,preference, category));

        return categoryService.checkPreference(c);

    }
    



}

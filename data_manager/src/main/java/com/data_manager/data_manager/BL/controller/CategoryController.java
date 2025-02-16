package com.data_manager.data_manager.BL.controller;

import com.data_manager.data_manager.DAL.category.CategoryForChange;
import com.data_manager.data_manager.DAL.category.PreferenceForChange;
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
import com.data_manager.data_manager.DAL.category.CategoryForChangingAll;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@RestController("dataManager/userCategories")
@Log4j2
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("saveCategory")
    public String saveCategory (@RequestBody Category category){
        log.info("Save Category");

        return categoryService.saveCategory(category);
    }
    @GetMapping("getPreferenceByCategory")
    public List<String> getPreferenceByCategory (@RequestParam String email, @RequestParam String category){
        log.info("getPreferenceByCategory");
        return categoryService.getPreferencecByCategory(email, category);
    }
    @GetMapping("myCategories")
    public Map<String, List<String>> myCategories (@RequestParam String email){
        log.info("get Category");
            return categoryService.myCategories(email);
    }
    @DeleteMapping("deletePreference")
    public String deletePreference (@RequestBody Category category){
        log.info("delete Preference");
        return categoryService.deletePreference(category);

    }
    @DeleteMapping("deleteCategory")
    public String deleteCategory (@RequestParam String email,@RequestParam String category){
        log.info("delete Category");
        return categoryService.deleteCategory(email, category);
    }
    @PutMapping("updateCategory")
    public String updateCategory(@RequestBody CategoryForChange category, @RequestParam String email){
        log.info("updateCategory");
        return categoryService.updateCategory(category, email);
    }
    @PutMapping("updatePreference")
    public String updatePreference(@RequestBody PreferenceForChange preference, @RequestParam String email){
        log.info("updatePreference");
        return categoryService.updatePreference(preference, email);

    }
    @PutMapping("updateAll")
    public String updateAll( @RequestBody CategoryForChangingAll categories){
        log.info("updateAll");
        return categoryService.updateAll(categories);
    }

}

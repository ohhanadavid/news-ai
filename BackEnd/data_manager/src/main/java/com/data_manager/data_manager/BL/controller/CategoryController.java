package com.data_manager.data_manager.BL.controller;

import com.data_manager.data_manager.DTO.category.CategoryForChange;
import com.data_manager.data_manager.DTO.category.PreferenceForChange;
import com.data_manager.data_manager.DTO.user.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.data_manager.data_manager.BL.services.CategoryService;
import com.data_manager.data_manager.DTO.category.Category;
import com.data_manager.data_manager.DTO.category.CategoryForChangingAll;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@RestController("dataManager/userCategories")
@Log4j2
@RequestMapping("api")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("saveCategory")
    public String saveCategory (@RequestBody Category category,@AuthenticationPrincipal Jwt jwt){
        log.info("Save Category");

        return categoryService.saveCategory(category,new UserData(jwt));
    }

    @GetMapping("getPreferenceByCategory")
    public List<String> getPreferenceByCategory ( @RequestParam String category,@AuthenticationPrincipal Jwt jwt){
        log.info("getPreferenceByCategory");
        return categoryService.getPreferenceByCategory( category,new UserData(jwt));
    }

    @GetMapping("myCategories")
    public Map<String, List<String>> myCategories (@AuthenticationPrincipal Jwt jwt){
        log.info("get Category");
            return categoryService.myCategories(new UserData(jwt));
    }

    @DeleteMapping("deletePreference")
    public String deletePreference (@RequestBody Category category,@AuthenticationPrincipal Jwt jwt){
        log.info("delete Preference");
        return categoryService.deletePreference(category,new UserData(jwt));

    }

    @DeleteMapping("deleteCategory")
    public String deleteCategory (@RequestParam String category,@AuthenticationPrincipal Jwt jwt){
        log.info("delete Category");
        return categoryService.deleteCategory( category,new UserData(jwt));
    }

    @PutMapping("changeCategory")
    public String updateCategory(@RequestBody CategoryForChange category,@AuthenticationPrincipal Jwt jwt){
        log.info("updateCategory");
        return categoryService.updateCategory(category,new UserData(jwt));
    }

    @PutMapping("updatePreference")
    public String updatePreference(@RequestBody PreferenceForChange preference,@AuthenticationPrincipal Jwt jwt){
        log.info("updatePreference");
        return categoryService.updatePreference(preference,new UserData(jwt));

    }


}

package com.data_manager.data_manager.BL.services;

import java.util.List;
import java.util.Map;

import com.data_manager.data_manager.DAL.modol.category.CategoryKeyForDB;
import com.data_manager.data_manager.DAL.srevice.CategoryToDBService;
import com.data_manager.data_manager.DTO.category.*;
import com.data_manager.data_manager.DTO.user.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class CategoryService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
    @Autowired
    CategoryToDBService categoryToDBService;
    


    public String saveCategory (Category category, UserData user){
        log.info("Save Category for {}",user.getUserID());


        checkPreference(category.getCategory().toCategoryKeyForDB(user.getUserID()));

        checkCategory(category.getCategory().getCategory());


        categoryToDBService.saveCategory(category.toCategoryToDB(user.getUserID()));

        return "Category Saved!";

   }


    public List<String> getPreferenceByCategory(String category, UserData user){
        log.info("getPreferenceByCategory for {}",user.getUserID());
        return categoryToDBService.getPreferencesByCategory(user.getUserID(),category);

    }
    

    public Map<String,List<String>> myCategories (UserData user){
        log.info("get Category for {}",user.getUserID());

          return categoryToDBService.myCategories(user.getUserID());

    }

    public String deletePreference(Category category, UserData user){
        log.info("delete Preference for {}",user.getUserID());
        categoryToDBService.deletePreferences(category.toCategoryToDB(user.getUserID()));
        return "preference deleted!";

    }
    
    public String deleteCategory (String category, UserData user){
        log.info("delete Category FOR {}",user.getUserID());
        categoryToDBService.deleteCategory(user.getUserID(),category);
        return "category deleted!";

    }

    public String updateCategory( CategoryForChange category, UserData user){
        log.info("updateCategory for {}",user.getUserID());

        checkCategory(category.getNewCategory());
        UriComponents url;

        Map<String,List<String>> response = categoryToDBService.myCategories(user.getUserID());

        if(!response.containsKey(category.getOldCategory()))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);

        if(response.containsKey(category.getNewCategory())){
            List<String> newCategoryList=response.get(category.getNewCategory());
            newCategoryList.retainAll(response.get(category.getOldCategory()));

            newCategoryList.forEach(x->
                    deletePreference(new Category(new CategoryKeyFromUser( x, category.getOldCategory())),user) );
        }
        categoryToDBService.updateCategory(category.getOldCategory(),category.getNewCategory(),user.getUserID());

        return "category updated!";
    }

    private void checkCategory(String category) {
        UriComponents url= UriComponentsBuilder.fromHttpUrl(newsAiAccessorUrl).
                path("api.checkCategory"+"/").
                path(category).
                build();
        Boolean res= restTemplate.getForObject(url.toUriString(), Boolean.class);
        if(res == null)
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        if(!res)
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        
    }

    public String updatePreference(PreferenceForChange preference, UserData user){
        log.info("updatePreference for {}",user.getUserID());

        checkPreference(new CategoryKeyForDB(user.getUserID(), preference.getNewPreference(),preference.getCategory()));

        categoryToDBService.updatePreference(preference,user.getUserID());

        return "Preference update";

    }
    


    private void checkPreference( CategoryKeyForDB categoryKeyForDB) {
        boolean res = categoryToDBService.checkPreference(categoryKeyForDB);
        if(res) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT);
        }

    }

}

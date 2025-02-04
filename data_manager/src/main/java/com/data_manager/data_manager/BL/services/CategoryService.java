package com.data_manager.data_manager.BL.services;

import java.util.List;
import java.util.Map;

import com.data_manager.data_manager.DAL.category.*;
import com.data_manager.data_manager.DAL.languege.LanguageKey;
import com.data_manager.data_manager.Exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class CategoryService implements ICategoryService {
    @Autowired
    private IChecking checking;
    @Autowired
    RestTemplate restTemplate;
    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;
    


    public ResponseEntity<?> saveCategory (Category category){
        log.info("Save Category");
        String email=category.getCategory().getEmail();
        String categoryString=category.getCategory().getCategory();
        String preference=category.getCategory().getPreference();
        Boolean check=checking.checkUser(category.getCategory().getEmail());
        if(check)
            throw new ItemNotFoundException("user not found");
        checkPreference(email,preference,categoryString);
        checkCategory(category.getCategory().getCategory());
        UriComponents url = UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                path("api.saveCategory").build();

        restTemplate.postForObject(url.toUriString(),category,Category.class);
        return new ResponseEntity<>("Category Saved!",HttpStatus.OK);

   }

    @Override
    public ResponseEntity<?> getPreferencecByCategory (String email,String category){
        log.info("getPreferenceByCategory");

            UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                    path("api.getPreferenceByCategory"+"/").
                    path(email+"/").
                    path(category).
                    build();
            List<String> response = restTemplate.getForObject(url.toUriString(), List.class);
            if (response == null  )
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>( response,HttpStatus.OK);

    }
    
    @Override
    public ResponseEntity<?> myCategories (String email){
        log.info("get Category");

            UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                    path("api.myCategories"+"/").
                    path(email).
                    build();
            Map<String,List<String>> response = restTemplate.getForObject(url.toUriString(), Map.class);
           if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>( response,HttpStatus.OK);

    }

    public ResponseEntity<?> deletePreference(Category category){
        log.info("delete Preference");

            UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.deletePreference")
                    .build();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Category> requestEntity = new HttpEntity<>(category, headers);
        restTemplate.exchange(url.toUriString(), HttpMethod.DELETE,requestEntity,ResponseEntity.class);

            
        return new ResponseEntity<>("preference deleted!",HttpStatus.OK);

    }
    
    public ResponseEntity<?> deleteCategory (String email,String category){
        log.info("delete Category");

            UriComponents url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                    .path("api.deleteCategory"+"/")
                    .path(email)
                    .queryParam("category",category)
                    .build();
            restTemplate.delete(url.toUriString());
            
            return new ResponseEntity<>("category deleted!",HttpStatus.OK);

    }

    public ResponseEntity<?> updateCategory( CategoryForChange category,String email){
        log.info("updateCategory");

        checkCategory(category.getNewCategory());
        UriComponents url;

        url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                    path("api.myCategories"+"/").
                    path(email).
                    build();
        Map<String,List<String>> response = restTemplate.getForObject(url.toUriString(), Map.class);
        if (response == null)
            return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            
        if(!response.containsKey(category.getOldCategory()))
            return new ResponseEntity<>(String.format("this category %s not exists",category.getOldCategory()),HttpStatus.BAD_REQUEST);

        if(response.containsKey(category.getNewCategory())){
            List<String> newCategoryList=response.get(category.getNewCategory());
            List<String> toUpdate=response.get(category.getOldCategory());
            toUpdate.forEach(x->{
                if(newCategoryList.contains(x)){
                    deletePreference(new Category(new CategoryKey(email, x, category.getOldCategory())));
                }
            }
            );
        }
        url=UriComponentsBuilder.fromHttpUrl(userAccessorUrl).path("/api.updateCategory/").path(email).build();
        restTemplate.put(url.toUriString(),category);
        return new ResponseEntity<>("category updated!",HttpStatus.OK);
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

    public ResponseEntity<?> updatePreference(PreferenceForChange preference, String email){
        log.info("updatePreference");
        checkPreference(email,preference.getNewPreference(),preference.getCategory());
        UriComponents url = UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                .path("api.updatePreference" + "/")
                .path(email)
                .build();
            restTemplate.put(url.toUriString(),preference);
            return new ResponseEntity<>("Preference update",HttpStatus.OK);

    }
    
    public ResponseEntity<?> updateAll( CategoryForChangingAll categories){
        log.info("updateAll");


        String newCategory=categories.getNewCategory();

        checkCategory(newCategory);
        checkPreference(categories.getEmail(),categories.getNewPreference(),categories.getNewCategory());
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(userAccessorUrl)
                .path("api.updateAll")
                .build();
            restTemplate.put(url.toUriString(),categories);
            return new ResponseEntity<>("category update!",HttpStatus.OK);
    }

    private void checkPreference( String email,String preference,String category) {
        Boolean res;
        UriComponents url;
        url= UriComponentsBuilder.fromHttpUrl(userAccessorUrl).
                path("api.checkPreference/").
                path(email).
                queryParam("preference", preference).
                queryParam("category", category).
                build();
        res = restTemplate.getForObject(url.toUriString(), Boolean.class);
        if(res == null) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(res) {
            throw new HttpServerErrorException(HttpStatus.CONFLICT);
        }

    }

}

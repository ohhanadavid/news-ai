package com.data_manager.data_manager.BL.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.data_manager.data_manager.DAL.category.Category;
import com.data_manager.data_manager.DAL.category.CategoryForChange;
import com.data_manager.data_manager.DAL.category.CategoryKey;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CategoryService implements ICategoryService {
    @Autowired
    private IChecking checking;
    @Autowired
    private DaprClient daprClient;
    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;
    
    private final String  operation="create";
    String url;
    public ResponseEntity<?> saveCategory (Category category){
        log.info("Save Category");
        try{
            String email=category.getCategory().getEmail();
            String categoryString=category.getCategory().getCategory();
            String preferencec=category.getCategory().getPreferencec();
            ResponseEntity<?> check=checking.checkUser(category.getCategory().getEmail());
            if(check.getStatusCode()!= HttpStatus.OK ||!(boolean)check.getBody() )
                return  check;
            
            url=String.format("api.checkPreferencec/%s/%s/%s",email,preferencec,categoryString);
            Boolean res=daprClient.invokeMethod(userAccessorUrl,url,null,HttpExtension.GET,Boolean.class).block();

            if(res == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            if(res)
                return new ResponseEntity<>("you have this Preferencec!",HttpStatus.BAD_REQUEST);
            url=String.format("api.checkCategory/%s", category.getCategory().getCategory()); 
            res=daprClient.invokeMethod(newsAiAccessorUrl,url,null,HttpExtension.GET,Boolean.class).block();
            if(res == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            if(!res)
                return new ResponseEntity<>("this category not allow!",HttpStatus.BAD_REQUEST);  

           daprClient.invokeBinding("api.saveCategory", operation,category).block();  
       
            
            return new ResponseEntity<>("category saved!",HttpStatus.OK);
           
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getPreferencecByCategory (String email,String category){
        log.info("getPreferencecByCategory");
        try{  
            
            url=String.format("api.getPreferencecByCategory/%s/%s",email,category);
            List<String> response = daprClient.invokeMethod(userAccessorUrl, url,null,HttpExtension.GET,List.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>( response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<?> myCategories (String email){
        log.info("get Category");
        try{  
            url=String.format("api.myCategories/%s",email); 
            Map<String,List<String>> response = daprClient.invokeMethod(userAccessorUrl, url,null,HttpExtension.GET,Map.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>( response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deletePreferencec (Category category){
        log.info("delete Preferencec");
        try {

            daprClient.invokeBinding("api.deletePreferencec", operation,category).block();  
       
            
            return new ResponseEntity<>("preferencec deleted!",HttpStatus.OK);
            
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public ResponseEntity<?> deleteCategory (String email,String category){
        log.info("delete Preferencec");
        
        try {
            Map<String,String> data = new HashMap<>();
            data.put("email",email);
            data.put("category",category);
            daprClient.invokeBinding("api.deleteCategory", operation,data).block();  
       
            
            return new ResponseEntity<>("category deleted!",HttpStatus.INTERNAL_SERVER_ERROR);
            
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateCategory( String oldCategory, String newCategory,String email){
        log.info("updateCategory");
        
        try {
            url=String.format("api.checkCategory/%s",newCategory); 
            Boolean res=daprClient.invokeMethod(newsAiAccessorUrl,url,null,HttpExtension.GET,Boolean.class).block();
            ///
            
            if(res == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            if(!res)
                return new ResponseEntity<>("this category no allowe",HttpStatus.BAD_REQUEST);
            ///           
            url=String.format("api.myCategories/%s",email); 
            Map<String,List<String>> response = daprClient.invokeMethod(userAccessorUrl, url,null,HttpExtension.GET,Map.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            
            if(!response.containsKey(oldCategory))
                return new ResponseEntity<>(String.format("this category %s not exists",oldCategory),HttpStatus.BAD_REQUEST);
            if(response.containsKey(newCategory)){
                List<String> newCategoryList=response.get(oldCategory);
                List<String> toUpdate=response.get(oldCategory);
                toUpdate.forEach(
                    x->{
                        if(newCategoryList.contains(x)){
                            deletePreferencec(new Category(new CategoryKey(email, x, oldCategory)));
                        }
                    }
                );
            }    

            Map<String,String> data = new HashMap<>();
            data.put("oldCategory",oldCategory);
            data.put("newCategory",newCategory);
            data.put("email",email);
            daprClient.invokeBinding("api.updateCategory", operation,data).block();  
       
            return new ResponseEntity<>("category updated!",HttpStatus.OK);
            
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    public ResponseEntity<?> updatePreferencec( String oldPreferencec, String newPreferencec,String email,String category){
        log.info("updatePreferencec");
        
        try {
            
           
            
            url=String.format("api.checkPreferencec/%s/%s/%s",email,newPreferencec,category);
            Boolean res=daprClient.invokeMethod(userAccessorUrl,url,null,HttpExtension.GET,Boolean.class).block();
    
            if(res == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            if(res)
                return new ResponseEntity<>("you have this Preferencec!",HttpStatus.BAD_REQUEST);
            
            Map<String,String> data = new HashMap<>();
            data.put("oldPreferencec",oldPreferencec);
            data.put("newPreferencec",newPreferencec);
            data.put("email",email);
            data.put("category",category);
            daprClient.invokeBinding("api.updatePreferencec", operation,data).block();  
       
           
            return new ResponseEntity<>("Preferencec update",HttpStatus.OK);
            
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public ResponseEntity<?> updateAll( CategoryForChange categories){
        log.info("updateAll");
        if(!categories.getOldCategory().getCategory().getEmail().equals(categories.getNewCategory().getCategory().getEmail())){
            log.error("email must be equals!");
            return new ResponseEntity<>(new IllegalArgumentException("email must be equals!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            String newCategory=categories.getNewCategory().getCategory().getCategory();
            url=String.format("api.checkCategory/%s",newCategory);
            Boolean res=daprClient.invokeMethod(newsAiAccessorUrl,url,null,HttpExtension.GET,Boolean.class).block();
            if(res == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            if(!res) 
                return new ResponseEntity<>("this category no allowe",HttpStatus.BAD_REQUEST);
            String email=categories.getNewCategory().getCategory().getEmail();
            String categoryString=categories.getNewCategory().getCategory().getCategory();
            String preferencec=categories.getNewCategory().getCategory().getPreferencec();
            url=String.format("api.checkPreferencec/%s/%s/%s",email,preferencec,categoryString);
            res=daprClient.invokeMethod(userAccessorUrl,url,null,HttpExtension.GET,Boolean.class).block();
    
            if(res == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            if(res)
                return new ResponseEntity<>("you have this Preferencec!",HttpStatus.BAD_REQUEST);
            
            daprClient.invokeBinding("api.updateAll", operation,categories).block();  
       
            
            return new ResponseEntity<>("category update!",HttpStatus.OK);
            

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
}

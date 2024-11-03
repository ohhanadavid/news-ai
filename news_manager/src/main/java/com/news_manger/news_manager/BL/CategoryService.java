package com.news_manger.news_manager.BL;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CategoryService implements ICategoryService {

    @Autowired
    private DaprClient daprClient;
  
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;
    
    
    String url;
    
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

   
   
}

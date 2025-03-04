package com.news_manger.news_manager.BL.servises;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class CategoryService implements ICategoryService {

    @Autowired
    private RestTemplate restTemplate;
  
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;

    @Override
    public ResponseEntity<?> getPreferenceByCategory(String email, String category){
        log.info("getPreferenceByCategory");

        String url=String.format("%s/api.getPreferenceByCategory/%s/%s",userAccessorUrl,email,category);
        List<String> response = restTemplate.getForObject(url, List.class);

        if (response == null)
            return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>( response,HttpStatus.OK);

    }
    
    @Override
    public ResponseEntity<?> myCategories (String email){
        log.info("get Category");

          String url=String.format("%s/api.myCategories/%s",userAccessorUrl,email);
        Map<String,List<String>> response = restTemplate.getForObject(url, Map.class);
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>( response,HttpStatus.OK);

    }

   
   
}

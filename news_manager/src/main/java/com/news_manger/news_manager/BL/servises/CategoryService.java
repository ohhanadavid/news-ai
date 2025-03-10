package com.news_manger.news_manager.BL.servises;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    private RestTemplate restTemplate;
  
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;

    @Override
    public List<String> getPreferenceByCategory( String category){
        log.info("getPreferenceByCategory");
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(userAccessorUrl)
                .path("getPreferenceByCategory")
                .queryParam("category",category)
                .build();

        List<String> response = restTemplate.getForObject(uri.toUriString(), List.class);

        if (response == null)
            throw  new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }
    
    @Override
    public Map<String, List<String>> myCategories (){
        log.info("get Category");

        String url=String.format("%s/myCategories",userAccessorUrl);
        Map<String,List<String>> response = restTemplate.getForObject(url, Map.class);
        if (response == null)
            throw  new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }

   
   
}

package com.news_manger.news_manager.BL;



import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class NewsController {
    @Autowired
    private NewsService newsDataService;
    private final String defaultForArticle ="3";
   
    @GetMapping("getLatestNews")
    public ResponseEntity<?> getLatestNews(@RequestParam String email,@RequestParam (required = false,defaultValue = defaultForArticle)int numberOfArticle) throws JsonProcessingException {
        log.info("getLatestNews");
        return newsDataService.getLatestNews( email, numberOfArticle);

    }

    @GetMapping("getLatestNewsByCategory")
    public ResponseEntity<?> getLatestNewsByCategory(@RequestParam String email,@RequestParam String category,@RequestParam (required = false,defaultValue = defaultForArticle)int numberOfArticle) throws JsonProcessingException {
        log.info("getLatestNewsByCategory");
        return newsDataService.getLatestNewsByCategory(email,category,numberOfArticle);

    }

    @GetMapping("getLatestListNewsByCategories")
    public ResponseEntity<?> getLatestListNewsFromCategories(String email,@RequestParam (required = false,defaultValue = defaultForArticle)int numberOfArticle) throws JsonProcessingException {
        log.info("getLatestListNewsByCategories");
        return newsDataService.getLatestListNewsFromCategories(email,numberOfArticle);

    }


    @GetMapping("getCategories")
    public ResponseEntity<?> getCategories() {
        log.info("getCategories");
        return newsDataService.getCategories();

        
    }

    @GetMapping("getLanguages")
    public ResponseEntity<?> getLanguages() {
        log.info("getLanguages");
        return newsDataService.getLanguages();

        
    }

    @GetMapping("checkCategory")
    public ResponseEntity<?> checkCategory(@RequestParam String category) {
        log.info("checkCategory");
        return newsDataService.checkCategory(category);

        
    }
    @GetMapping("getLanguageCode")
    public ResponseEntity<?> getLanguageCode(@RequestParam String language) {
        log.info("getLanguageCode");
        return newsDataService.getLanguageCode(language);

        
    }

    @GetMapping("maximumLanguage")
    public ResponseEntity<?> getMaximumLanguage() {
        log.info("api.maximumLanguage");
        return newsDataService.getMaximumLanguage() ;

    }





    
}

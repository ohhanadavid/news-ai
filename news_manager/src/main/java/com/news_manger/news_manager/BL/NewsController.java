package com.news_manger.news_manager.BL;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.news_manger.news_manager.BL.NewsService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class NewsController {
    @Autowired
    private NewsService newsDataService;
    private final String defultForArticle="3";
   
    @GetMapping("getLatestNews")
    public ResponseEntity<?> getLatestNews(@RequestParam String email,@RequestParam (required = false,defaultValue =defultForArticle)int numberOfArtical) {
        try{
            log.info("getLatestNews");
            return newsDataService.getLatestNews( email, numberOfArtical);
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }

    @GetMapping("getLatestNewsByCategory")
    public ResponseEntity<?> getLatestNewsByCategory(@RequestParam String email,@RequestParam String category,@RequestParam (required = false,defaultValue =defultForArticle)int numberOfArtical) {
        try{
            log.info("getLatestNews");
            return newsDataService.getLatestNewsByCategory(email,category,numberOfArtical);
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }

    @GetMapping("getLatestListNewsByCategories")
    public ResponseEntity<?> getLatestListNewsFromCategories(String email,@RequestParam (required = false,defaultValue =defultForArticle)int numberOfArtical) {
        try{
            log.info("getLatestListNewsByCategories");
        return newsDataService.getLatestListNewsFromCategories(email,numberOfArtical);
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }


    @GetMapping("getCategories")
    public ResponseEntity<?> getCategories() {
        log.info("getCategories");
        try{
            return newsDataService.getCategories();
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("getLanguages")
    public ResponseEntity<?> getLanguages() {
        log.info("getCategories");
        try{
            return newsDataService.getLanguages();
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("checkCategory")
    public ResponseEntity<?> checkCategory(@RequestParam String category) {
        log.info("checkCategory");
        try{
            return newsDataService.checkCategory(category);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    @GetMapping("getLanguageCode")
    public ResponseEntity<?> getLanguageCode(@RequestParam String language) {
        log.info("getLanguageCode");
        try{
            return newsDataService.getLanguageCode(language);
        }
        catch(Exception e){
            log.error("getLanguageCode "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("maximumLanguage")
    public ResponseEntity<?> getMaximumLanguage() {
        log.info("api.maximumLanguage");
        try{
            return newsDataService.getMaximumLanguage() ;
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("getNews")
    public void getNews(@RequestBody Map<String,Object>data){
        try{
            
            newsDataService.getNews(data);
        }catch(Exception e){
            log.error(e.getMessage());
            
        }
    }

    @PostMapping("getListNews")
    public void getListNews(@RequestBody Map<String,Object>data) {
        try{
            
            newsDataService.getListNews(data);
        }catch(Exception e){
            log.error(e.getMessage());
            
        }
    }
    

    @PostMapping("gimeniAnswer")
    public void getFilterNews(@RequestBody Map<String,Object>data){
        try{
            
            newsDataService.getFilterNews(data);
        }catch(Exception e){
            log.error(e.getMessage());
            
        }
    }

    @GetMapping()
    public Boolean healthCheck(){
        return true;
    }

    
}

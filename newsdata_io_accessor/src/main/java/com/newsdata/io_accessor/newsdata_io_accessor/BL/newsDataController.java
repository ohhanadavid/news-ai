package com.newsdata.io_accessor.newsdata_io_accessor.BL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController

public class newsDataController {
    @Autowired
    private NewsDataService newsDataService;
    private final Integer maximumLanguages=5;

  
    
    @PostMapping("/api.getLatestNews")
    public ResponseEntity<?> getLatestNews(@RequestBody Map<String,Object> data) {
        try{
            log.info("getLatestNews");
            newsDataService.getLatestNews(data);
        return new ResponseEntity<>( HttpStatus.OK);
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }

    @PostMapping("/api.getLatestNewsByCategory")
    public ResponseEntity<?> getLatestNewsByCategory(@RequestBody Map<String,Object> data) {
        try{
            log.info("getLatestNewsByCategory");
        String category=(String)data.get("category");
        newsDataService.getLatestNewsFromTopic(category,data);
        return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }

    @PostMapping("/api.getLatestListNewsByCategories")
    public ResponseEntity<?> getLatestListNewsFromCategories(@RequestBody Map<String,Object> data) {
        try{
            log.info("getLatestListNewsByCategories");
            
        return new ResponseEntity<>( newsDataService.getLatestListNewsFromCategories(data),HttpStatus.OK);
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }

    @GetMapping()
    public Boolean healthCheck(){
        return true;
    }

   
   
    @GetMapping("/api.getCategories")
    public List<String> getCategories() {
        log.info("getCategories");
        try{
            return newsDataService.getCategories();
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
        
    }

    @GetMapping("/api.getLanguages")
    public Map<String,String> getLanguages() throws Exception {
        log.info("getCategories");
        try{
            return newsDataService.getLangueges();
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
        
    }

    @GetMapping("/api.checkCategory/{category}")
    public Boolean checkCategory(@PathVariable String category) {
        log.info("checkCategory");
        try{
            Boolean res=newsDataService.getCategories().stream().anyMatch(x->x.equals(category));
            
            return res;
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
        
    }
   
    @GetMapping("/api.getLanguageCode/{language}")
    public Map<String,String> getLanguageCode(@PathVariable String language) throws Exception  {
        log.info("getLanguageCode");
        try{
            Map<String,String> data= new HashMap<>();
            data.put("code",newsDataService.getLangueges().get(language));
            return data;
        }
        catch(Exception e){
            log.error("getLanguageCode "+e.getMessage());
            throw e;
        }
        
    }

    @GetMapping("/api.maximumLanguage")
    public Integer getMaximumLanguage() {
        return maximumLanguages;
    }
    

    
    
    
    

}

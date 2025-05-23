package com.news_manger.news_manager.BL;

import com.news_manger.news_manager.BL.servises.NewsAIAccessorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("api")
public class newsDataController {
    @Autowired
    private NewsAIAccessorService newsDataService;

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
    public Set<String> getLanguages() throws Exception {
        log.info("getLanguages");
        try{
            return newsDataService.getLanguagesAsMap().keySet();
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
            return newsDataService.getCategories().stream().anyMatch(x->x.equals(category));
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
        
    }

    @GetMapping("/api.checkLanguage/{language}")
    public Boolean checkLanguage(@PathVariable String language) throws Exception {
        log.info("checkLanguage");
        try{
            return newsDataService.getLanguagesAsList().stream().anyMatch(x->x.getLanguage().equals(language));
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }

    }
   
    @GetMapping("/api.getLanguageCode/{language}")
    public String getLanguageCode(@PathVariable String language) throws Exception  {
        log.info("getLanguageCode");
        try{
           return newsDataService.getLanguagesAsMap().get(language);
        }
        catch(Exception e){
            log.error("getLanguageCode {}", e.getMessage());
            throw e;
        }
        
    }

    @GetMapping("/api.maximumLanguage")
    public Integer getMaximumLanguage() {
        return 5;
    }
    

    
    
    
    

}

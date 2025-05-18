package com.news_manger.news_manager.BL;



import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.news_manger.news_manager.BL.servises.NewsAIService;
import com.news_manger.news_manager.DTO.articals.ArticleForView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.extern.log4j.Log4j2;

@RestController("newsManager")
@Log4j2
@RequestMapping("api")
public class NewsController {
    @Autowired
    private NewsAIService newsDataService;
    private final String defaultForArticle ="3";
   



    @GetMapping("getCategories")
    public List<String> getCategories() {
        log.info("getCategories");
        return newsDataService.getCategories();

        
    }

    @GetMapping("getLanguages")
    public Set<String> getLanguages() throws Exception {
        log.info("getLanguages");
        return newsDataService.getLanguages();

        
    }

    @GetMapping("checkCategory")
    public Boolean checkCategory(@RequestParam String category) {
        log.info("checkCategory");
        return newsDataService.checkCategory(category);

        
    }

    @GetMapping("getLanguageCode")
    public String getLanguageCode(@RequestParam String language) throws Exception {
        log.info("getLanguageCode");
        return newsDataService.getLanguageCode(language);

    }

    @GetMapping("maximumLanguage")
    public Integer getMaximumLanguage() {
        log.info("api.maximumLanguage");
        return newsDataService.getMaximumLanguage() ;

    }

    @GetMapping("getNewsView")
    public List<ArticleForView> getArticleForView(@AuthenticationPrincipal Jwt jwt) throws JsonProcessingException {
        log.info("getNewsView");
        return newsDataService.getArticleForView(jwt);
    }

//    @GetMapping("tinyURL/{tiny}")
//    public ModelAndView getTiny(@PathVariable String tiny){
//        String url = newsDataService.tinyUrl(tiny);
//        return new ModelAndView("redirect:" + url);
//    }

}

package com.news_manger.news_manager.BL;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.news_manger.news_manager.DAL.NewTinyRequest;
import com.news_manger.news_manager.DAL.articals.*;
import com.news_manger.news_manager.DAL.articalsToGet.*;
import com.news_manger.news_manager.DAL.notification.MailData;
import com.news_manger.news_manager.kafka.KafkaTopic;
import com.news_manger.news_manager.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news_manger.news_manager.DAL.user.User;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class NewsService {

    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
//    @Value("${TinyUrlURL}")
//    private String tinyUrl_Url;

    @Autowired
    private IChecking checking;
    @Autowired
    private ILanguageService languageService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier("kafkaRestTemplate")
    RestTemplate restTemplate;
    @Autowired
    private Producer producer;
    
    public ResponseEntity<?> getLatestNews(String email,int numberOfArticle ) throws JsonProcessingException {
        log.info("getLatestNews");
        List<String> languagesCode = getLanguagesCode(email);

        DataForNews data=new DataForNews(numberOfArticle,email,languagesCode);


        producer.send(data, KafkaTopic.GET_LATEST_NEWS);
        return new ResponseEntity<>( "email send!",HttpStatus.valueOf(200));
        

    }

    private List<String> getLanguagesCode(String email) {
        ResponseEntity<?> languageResponse=languageService.getLanguagesCode(email);
        checking.checkResponse(languageResponse, List.class);
        List<String> languagesCode=(List<String>)languageResponse.getBody();
        return languagesCode;
    }

    public ResponseEntity<?> getLatestNewsByCategory(String email,String category,int numberOfArticle) throws JsonProcessingException {
        log.info("getLatestNewsByCategory");

        ResponseEntity<?> categoryResponse=categoryService.getPreferenceByCategory(email,category);
        checking.checkResponse(categoryResponse, List.class);

        List<String> languagesCode = getLanguagesCode(email);

        DataForNewsWithOneCategory data=new DataForNewsWithOneCategory(numberOfArticle,email,languagesCode,category);
        producer.send(data,KafkaTopic.GET_LATEST_NEWS_BY_CATEGORY);

                        
        return new ResponseEntity<>( "email send!",HttpStatus.valueOf(200));
        

    }
   
    public ResponseEntity<?> getLatestListNewsFromCategories(String email,int numberOfArticle) throws JsonProcessingException {
       
        log.info("getLatestListNewsByCategories");
        ResponseEntity<?> categoryResponse=categoryService.myCategories(email);
        checking.checkResponse(categoryResponse, Map.class);

        Map<String,List<String>> categories=((Map<String,List<String>>)categoryResponse.getBody());
        if(categories.isEmpty())
            return getLatestNews(email,numberOfArticle );

        List<String> languagesCode = getLanguagesCode(email);


        DataLists dataForNews=new DataLists(new ArrayList<>(categories.keySet()));

        DataForNewsWithCategorys data=new DataForNewsWithCategorys(numberOfArticle,email,languagesCode,dataForNews);

        producer.send(data,KafkaTopic.GET_LATEST_LIST_NEWS_BY_CATEGORIES);

        return new ResponseEntity<>( "email send!",HttpStatus.valueOf(200));
        

    }

    public void getNews(Map<String,Object> data) throws IOException {

        ArticleResults articles=new ArticleResults();
        if(data.get("article") instanceof String){
            byte[] decoded=Base64.getDecoder().decode((String)data.get("article"));
            String originalString = new String(decoded, StandardCharsets.UTF_8);
            articles=objectMapper.readValue(decoded,ArticleResults.class);
        }
        if(articles.getResults()==null){
            log.error("results is null");
            return;
        }
        int numberOfArticle=(int)data.get("numberOfArticle");
        User user=objectMapper.convertValue(data.get("to"), User.class) ;
            
        ResponseEntity<?> categoryResponse=categoryService.myCategories(user.getEmail());
        checking.checkResponse(categoryResponse, Map.class);


        Map<String,List<String>> categories=((Map<String,List<String>>)categoryResponse.getBody());

        List<ArticleReturn> articleReturns=articles.getResults().stream().map(a->new ArticleReturn(a)).toList();

        data.clear();
            
        categoryService.myCategories (user.getEmail());
        objectMapper.registerModule(new JavaTimeModule());

        data.put("article",objectMapper.writeValueAsBytes(articleReturns));
        data.put("numberOfArticle",numberOfArticle);
        data.put("preference",categories);
        data.put("to",user);

        producer.send(data,KafkaTopic.GET_MY_ARTICLE);


    }

    public void getListNews(ReturnData data) throws JsonMappingException, JsonProcessingException{

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //List<String> articles=(List<String>)(data.get("article"));
            
            if(data.getArticle() == null){
                log.error("results is null");
                return;
            }

            ResponseEntity<?> categoryResponse=categoryService.myCategories(data.getTo());
            checking.checkResponse(categoryResponse, Map.class);
            Map<String,List<String>> categories=((Map<String,List<String>>)categoryResponse.getBody());
            
            List<Article> results=new LinkedList<>();
            data.getArticle().forEach(a->{
                try {
                    ArticleResults art= objectMapper.readValue(a,ArticleResults.class);
                    if(art!=null)
                        results.addAll(art.getResults());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                 
                
            });
            
            
            List<ArticleReturn> articlesReturn=results.stream().map(ArticleReturn::new).toList();

           // categoryService.myCategories (user.getEmail());
        ArticlesToFilter articles= new ArticlesToFilter()
                .setArticleReturnList(articlesReturn)
                .setPreference(categories)
                .setData(data);
            producer.send(articles,KafkaTopic.GET_MY_ARTICLE);


    }
    
    public void getFilterNews( ArticleFromLLm data) throws Exception{

        log.info("getFilterNews");
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user= userService.getUser(data.getTo());


            
        StringBuilder articleToSend = new StringBuilder();

//        data.getArticles().parallelStream().forEach(s->{
//            NewTinyRequest tinyRequest = new NewTinyRequest(s.getUrl());
//            String tinyUrl = restTemplate.postForObject(tinyUrl_Url+"/tiny",tinyRequest, String.class);
//            s.setUrl(tinyUrl);
//        });

        data.getArticles().forEach(s -> articleToSend.append(s.toString()).append("\n"));

        MailData mail=new MailData()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSubject("Articles from newsAI")
                .setText(articleToSend.toString());

        producer.send(mail,KafkaTopic.SEND_EMAIL);


    }   
    
    public List<String> getCategories() {
        log.info("getCategories");
        String url=String.format("%s/api.getCategories",newsAiAccessorUrl);
        List<String> response =restTemplate.getForObject(url,List.class);
        if (response == null)
            throw new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }
  
    public Set<String> getLanguages() {
        log.info("getLanguages");
        String url=String.format("%s/api.getLanguages",newsAiAccessorUrl);
        Set<String> response =restTemplate.getForObject(url,Set.class);

        if (response == null)
            throw new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }
   
    public Boolean checkCategory( String category) {
        log.info("checkCategory");
        String url=String.format("%s/api.checkCategory/%s",newsAiAccessorUrl,category);
        Boolean response = restTemplate.getForObject( url,Boolean.class);
        if (response == null)
            throw new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }
    
    public String getLanguageCode( String language) {
        log.info("getLanguageCode");

        String url = String.format("%s/api.getLanguageCode/%s",newsAiAccessorUrl,language);
        String response = restTemplate.getForObject(url,String.class);
            if (response == null)
                throw new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR);
            return response;

        
    }
  
    public Integer getMaximumLanguage() {
        String url = String.format("%s/api.maximumLanguage",newsAiAccessorUrl);
        Integer response = restTemplate.getForObject(url,Integer.class);

        if (response == null)
            throw new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR);
        return response;

    }

//    public String tinyUrl(String tiny) {
//        return restTemplate.getForObject(tinyUrl_Url+"/"+tiny, String.class);
//    }
}

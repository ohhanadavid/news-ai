package com.news_manger.news_manager.BL;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news_manger.news_manager.DAL.articals.ArticalReturn;
import com.news_manger.news_manager.DAL.articals.Article;
import com.news_manger.news_manager.DAL.articals.ArticleResults;
import com.news_manger.news_manager.DAL.articals.DataLists;
import com.news_manger.news_manager.DAL.user.User;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class NewsService {
    @Autowired
    private DaprClient daprClient;
    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
    @Value("${GeminiAccessorUrl}")
    private String geminiAccessorUrl;
    @Value("${MailSenderEngine}")
    private String mailSenderEngine;
    @Autowired
    private IChecking checking;
    @Autowired
    private ILanguageService languageService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IUserService userService;
    String url; 
    String operation="create";
    @Autowired
    private ObjectMapper objectMapper;
    
    public ResponseEntity<?> getLatestNews(String email,int numberOfArtical ) {
        log.info("getLatestNews");
        try{
            var userResponse = userService.getUser(  email);
            userResponse = checking.checkResponse(userResponse, User.class);
            User user;
            if(userResponse.getStatusCode()!= HttpStatusCode.valueOf(200))
                return userResponse;
            user =(User)userResponse.getBody();
            Map <String,Object> data=new HashMap<>();
            data.put("numberOfArtical",numberOfArtical);
            data.put("to",user);
           daprClient.invokeBinding("api.getLatestNews",operation,data).block();
           return new ResponseEntity<>( "email send!",HttpStatus.valueOf(200));
        
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }
    
    public ResponseEntity<?> getLatestNewsByCategory(String email,String category,int numberOfArtical) {
        log.info("getLatestNews");
       
        try{
            var userResponse = userService.getUser(  email);
            userResponse = checking.checkResponse(userResponse, User.class);
            User user;
            if(userResponse.getStatusCode()!= HttpStatusCode.valueOf(200))
                return userResponse;
            user =(User)userResponse.getBody();
            ResponseEntity<?> categoryResponse=categoryService.getPreferencecByCategory ( email,category);
            ResponseEntity<?> check = checking.checkResponse(categoryResponse, List.class); 
            if(check.getStatusCode()!= HttpStatus.valueOf(200))
            return check;

            List<String> categoris=((List<String>)categoryResponse.getBody());
            if(categoris.isEmpty())
                return getLatestNews(email,numberOfArtical );

            url=String.format("api.getLatestNewsByCategory/%s",category);
            Map<String,Object> data =new HashMap<>();
            
            data.put("to",user);
            data.put("numberOfArtical",numberOfArtical);
            data.put("category",category);
            daprClient.invokeBinding("api.getLatestNewsByCategory",operation,data).block();
                        
            return new ResponseEntity<>( "email send!",HttpStatus.valueOf(200));
        
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }
   
    public ResponseEntity<?> getLatestListNewsFromCategories(String email,int numberOfArtical) {
       
        log.info("getLatestListNewsByCategories");
        
        try{
            var userResponse = userService.getUser(  email);
            userResponse = checking.checkResponse(userResponse, User.class);
            User user;
            if(userResponse.getStatusCode()!= HttpStatusCode.valueOf(200))
                return userResponse;
            user =(User)userResponse.getBody();
            ResponseEntity<?> categoryResponse=categoryService.myCategories(email);
            ResponseEntity<?> check = checking.checkResponse(categoryResponse, Map.class); 
            if(check.getStatusCode()!= HttpStatus.valueOf(200))
            return check;

            Map<String,List<String>> categoris=((Map<String,List<String>>)categoryResponse.getBody());
            if(categoris.isEmpty())
                return getLatestNews(email,numberOfArtical );
            ResponseEntity<?> languageResponse=languageService.getLanguegesCode(email);
            check = checking.checkResponse(languageResponse, List.class); 
            if(check.getStatusCode()!= HttpStatus.valueOf(200))
                return check;
            List<String> languagesCode=(List<String>)languageResponse.getBody();
            if(languagesCode.isEmpty())
                        return getLatestNews(email,numberOfArtical );
            Map<String,Object> data =new HashMap<>();
            DataLists dataForNews=new DataLists(new ArrayList<>(categoris.keySet()),languagesCode);
            data.put("to",user);
            data.put("numberOfArtical",numberOfArtical);
            data.put("dataForNews",dataForNews);
            

            
            daprClient.invokeBinding("api.getLatestListNewsByCategories",operation,data).block();
            
            
            return new ResponseEntity<>( "email send!",HttpStatus.valueOf(200));
        
        }catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>( e.getMessage(),HttpStatus.valueOf(500));
        }
    }
   
   

    public void getNews(Map<String,Object> data){
        try{
            ArticleResults articals=new ArticleResults();
            if(data.get("artical") instanceof String){
            byte[] decoded=Base64.getDecoder().decode((String)data.get("artical"));
            String originalString = new String(decoded, StandardCharsets.UTF_8);
            articals=objectMapper.readValue(decoded,ArticleResults.class);
            }
            if(articals.getResults()==null){
                log.error("results is null");
                return;
            }
            int numberOfArtical=(int)data.get("numberOfArtical");
            User user=objectMapper.convertValue(data.get("to"), User.class) ;
            
            ResponseEntity<?> categoryResponse=categoryService.myCategories(user.getEmail());
            ResponseEntity<?> check = checking.checkResponse(categoryResponse, Map.class); 
            if(check.getStatusCode()!= HttpStatus.valueOf(200))
                return;

            Map<String,List<String>> categoris=((Map<String,List<String>>)categoryResponse.getBody());
            List<ArticalReturn> articles=articals.getResults().stream().map(a->new ArticalReturn(a)).toList();
            data.clear();
            
            categoryService.myCategories (user.getEmail());
            objectMapper.registerModule(new JavaTimeModule());
            data.put("artical",objectMapper.writeValueAsBytes(articles));
            data.put("numberOfArtical",numberOfArtical);
            data.put("preferencec",categoris);
            data.put("to",user);
            daprClient.invokeBinding("api.getMyArtical","create",data).block();
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }

    public void getListNews(Map<String,Object> data) throws JsonMappingException, JsonProcessingException{
        try{
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<String> articals=(List<String>)(data.get("artical"));
            
            if(articals==null){
                log.error("results is null");
                return;
            }
            int numberOfArtical=(int)data.get("numberOfArtical");
            User user=objectMapper.convertValue( data.get("to"),User.class);
            
            ResponseEntity<?> categoryResponse=categoryService.myCategories(user.getEmail());
            ResponseEntity<?> check = checking.checkResponse(categoryResponse, Map.class); 
            if(check.getStatusCode()!= HttpStatus.valueOf(200))
                return;

            Map<String,List<String>> categoris=((Map<String,List<String>>)categoryResponse.getBody());
            
                List<Article> results=new LinkedList<>();
                articals.forEach(a->{

                    
                        try {
                            Article art= objectMapper.readValue(a,Article.class);
                            if(art!=null)
                                results.add(art);
                        } catch (JsonProcessingException e) {
                            
                            e.printStackTrace();
                        }
                 
                
                });
            
            
            List<ArticalReturn> articles=results.stream().map(a->new ArticalReturn(a)).toList();
            data.clear();
            categoryService.myCategories (user.getEmail());
            data.put("artical",articles);
            data.put("numberOfArtical",numberOfArtical);
            data.put("preferencec",categoris);
            data.put("to",user);
            daprClient.invokeBinding("api.getMyArtical","create",data).block();
        } catch(Exception e){
            log.error(e.getMessage());
        }
    }
    
    public void getFilterNews( Map<String,Object>data) throws Exception{
        try{
            log.info(data.get("getFilterNews"));
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            User user= objectMapper.convertValue(data.get("to"), User.class);
            String articalsString =(String)data.get("articals");
            List<ArticalReturn> articals=objectMapper.readValue(articalsString, new TypeReference<List<ArticalReturn>>(){});
            
            StringBuilder articlToSend = new StringBuilder();
            articals.forEach(s -> articlToSend.append(s.toString()).append("\n"));         
            Map <String,Object> mailData=new HashMap<>();
            mailData.put("to",user);
            mailData.put("articls",articlToSend);
            
            daprClient.invokeBinding("api.sendMail",operation,mailData).block();
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }   
    
    public ResponseEntity<?> getCategories() {
        log.info("getCategories");
      try{  
            
            List<String> response = daprClient.invokeMethod(newsAiAccessorUrl, "api.getCategories",null,HttpExtension.GET,List.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
    public ResponseEntity<?> getLanguages() {
        log.info("getCategories");
        try{  
            
            Map<String,String> response = daprClient.invokeMethod(newsAiAccessorUrl, "api.getLanguages",null,HttpExtension.GET,Map.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    public ResponseEntity<?> checkCategory( String category) {
        log.info("checkCategory");
        try{  
            url=String.format("api.checkCategory/%s",category);
            Boolean response = daprClient.invokeMethod(newsAiAccessorUrl, url,null,HttpExtension.GET,Boolean.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public ResponseEntity<?> getLanguageCode( String language) {
        log.info("getLanguageCode");
        try{  
            url=String.format("api.getLanguageCode/%s",language);
            Map<String,String> response = daprClient.invokeMethod(newsAiAccessorUrl, url,null,HttpExtension.GET,Map.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
  
    public ResponseEntity<?> getMaximumLanguage() {
        try{  
            
            Integer response = daprClient.invokeMethod(newsAiAccessorUrl, "api.maximumLanguage",null,HttpExtension.GET,Integer.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

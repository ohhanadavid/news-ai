package com.news_manger.news_manager.BL.servises;

import java.util.*;

import com.news_manger.news_manager.BL.IChecking;
import com.news_manger.news_manager.DAL.articals.*;
import com.news_manger.news_manager.DAL.articalsToGet.*;
import com.news_manger.news_manager.DAL.notification.MailData;
import com.news_manger.news_manager.DAL.user.UserRequest;
import com.news_manger.news_manager.DAL.user.UserRequestWithCategory;
import com.news_manger.news_manager.kafka.KafkaTopic;
import com.news_manger.news_manager.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news_manger.news_manager.DAL.user.User;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class NewsAIService {


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
    private Producer producer;

    @Autowired
    private NewsAIAccessorService newsAccessor;


    
    public void getLatestNews(UserRequest user ) throws JsonProcessingException {
        log.info("getLatestNews");
        List<String> languagesCode = getLanguagesCode(user.getUserID());

        DataForNews data=new DataForNews(user.getNumberOfArticles(),user.getUserID(),languagesCode);

        ReturnData returnData=newsAccessor.getLatestNews(data);

        getListNews(returnData);

        //producer.send(data, KafkaTopic.GET_LATEST_NEWS);

        

    }

    public void getLatestNewsByCategory(UserRequestWithCategory user) throws JsonProcessingException {
        log.info("getLatestNewsByCategory");

        List<String> languagesCode = getLanguagesCode(user.getUserID());

        DataForNewsWithOneCategory data=new DataForNewsWithOneCategory(user.getNumberOfArticles(),user.getUserID(),languagesCode,user.getCategory());
        ReturnData returnData= newsAccessor.getLatestNewsFromTopic(data);

        getListNews(returnData);
        //producer.send(data,KafkaTopic.GET_LATEST_NEWS_BY_CATEGORY);

    }
   
    public void getLatestListNewsFromCategories(UserRequest user) throws JsonProcessingException {
       
        log.info("getLatestListNewsByCategories");
        Map<String,List<String>> categories=categoryService.myCategories();

        if(categories.isEmpty()) {
            getLatestNews(new UserRequest());
            return;
        }

        List<String> languagesCode = getLanguagesCode(user.getUserID());


        DataLists dataForNews=new DataLists(new ArrayList<>(categories.keySet()));

        DataForNewsWithCategory data=new DataForNewsWithCategory(user.getNumberOfArticles(),user.getUserID(),languagesCode,dataForNews);

        ReturnData returnData=newsAccessor.getLatestListNewsFromCategories(data);
        //producer.send(data,KafkaTopic.GET_LATEST_LIST_NEWS_BY_CATEGORIES);

        getListNews(returnData);

    }

//    public void getNews(Map<String,Object> data) throws IOException {
//
//        ArticleResults articles=new ArticleResults();
//        if(data.get("article") instanceof String){
//            byte[] decoded=Base64.getDecoder().decode((String)data.get("article"));
//            String originalString = new String(decoded, StandardCharsets.UTF_8);
//            articles=objectMapper.readValue(decoded,ArticleResults.class);
//        }
//        if(articles.getResults()==null){
//            log.error("results is null");
//            return;
//        }
//        int numberOfArticle=(int)data.get("numberOfArticle");
//        User user=objectMapper.convertValue(data.get("to"), User.class) ;
//
//        ResponseEntity<?> categoryResponse=categoryService.myCategories(user.getEmail());
//        checking.checkResponse(categoryResponse, Map.class);
//
//
//        Map<String,List<String>> categories=((Map<String,List<String>>)categoryResponse.getBody());
//
//        List<ArticleReturn> articleReturns=articles.getResults().stream().map(a->new ArticleReturn(a)).toList();
//
//        data.clear();
//
//        categoryService.myCategories (user.getEmail());
//        objectMapper.registerModule(new JavaTimeModule());
//
//        data.put("article",objectMapper.writeValueAsBytes(articleReturns));
//        data.put("numberOfArticle",numberOfArticle);
//        data.put("preference",categories);
//        data.put("to",user);
//
//        producer.send(data,KafkaTopic.GET_MY_ARTICLE);
//
//
//    }

    public void getListNews(ReturnData data) throws JsonMappingException, JsonProcessingException{

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            
            if(data.getArticle() == null){
                log.error("results is null");
                return;
            }

            Map<String,List<String>> categories=categoryService.myCategories();

            
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
        return  newsAccessor.getCategories();

    }
  
    public Set<String> getLanguages() throws Exception {
        log.info("getLanguages");
        return newsAccessor.getLanguagesAsMap().keySet();

    }
   
    public Boolean checkCategory( String category) {
        log.info("checkCategory");
        return newsAccessor.getCategories().stream().anyMatch(x->x.equals(category));

    }
    
    public String getLanguageCode( String language) throws Exception {
        log.info("getLanguageCode");

        return newsAccessor.getLanguagesAsMap().get(language);

    }
  
    public Integer getMaximumLanguage() {
        return 5;
    }

    private List<String> getLanguagesCode(String userId) {

        return languageService.getLanguagesCode();

    }


}

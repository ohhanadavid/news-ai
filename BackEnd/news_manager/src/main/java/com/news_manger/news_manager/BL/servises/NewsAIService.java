package com.news_manger.news_manager.BL.servises;

import java.util.*;

import com.news_manger.news_manager.BL.IChecking;
import com.news_manger.news_manager.DTO.articals.*;
import com.news_manger.news_manager.DTO.articalsToGet.*;
import com.news_manger.news_manager.DTO.notification.NotificationData;
import com.news_manger.news_manager.DTO.user.SendOption;
import com.news_manger.news_manager.DTO.user.UserRequest;
import com.news_manger.news_manager.DTO.user.UserRequestWithCategory;
import com.news_manger.news_manager.configuration.TokenStorage;
import com.news_manger.news_manager.kafka.KafkaTopic;
import com.news_manger.news_manager.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news_manger.news_manager.DTO.user.User;

import lombok.extern.log4j.Log4j2;

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
    private TokenStorage tokenStorage;

    @Autowired
    private NewsAIAccessorService newsAccessor;


    
    public void getLatestNews(UserRequest user ) throws JsonProcessingException {
        log.info("getLatestNews");
        ReturnData returnData = getReturnData(user);

        getListNews(returnData);

    }

    public List<ArticleForView> getArticleForView(Jwt jwt) throws JsonProcessingException {
        log.info("getArticleForView ");
        tokenStorage.setToken(jwt.getTokenValue() );
        List<String> languagesCode = getLanguagesCode();
        List<String> articles= newsAccessor.getLatestNews(languagesCode);

        List<ArticleReturn> articlesReturn= getArticleType(articles);
        assert articlesReturn != null;
        List<ArticleForView> res = articlesReturn.stream().map(a->new ArticleForView(a.getTitle(),a.getUrl())).toList();
        return res;
    }



    private ReturnData getReturnData(UserRequest user) throws JsonProcessingException {
        List<String> languagesCode = getLanguagesCode();

        DataForNews data=new DataForNews(user.getNumberOfArticles(), user.getUserID(), user.getOption(),languagesCode);

        ReturnData returnData=new ReturnData(data,newsAccessor.getLatestNews(data.getLanguage()));
        return returnData;
    }

    public void getLatestNewsByCategory(UserRequestWithCategory user) throws JsonProcessingException {
        log.info("getLatestNewsByCategory");

        List<String> languagesCode = getLanguagesCode();

        DataForNewsWithOneCategory data=new DataForNewsWithOneCategory(user.getNumberOfArticles(),user.getUserID(),user.getOption(),languagesCode,user.getCategory());
        ReturnData returnData= newsAccessor.getLatestNewsFromTopic(data);

        getListNews(returnData);


    }
   
    public void getLatestListNewsFromCategories(UserRequest user) throws JsonProcessingException {
       
        log.info("getLatestListNewsByCategories");
        Map<String,List<String>> categories=categoryService.myCategories();

        if(categories.isEmpty()) {
            getLatestNews(new UserRequest());
            return;
        }

        List<String> languagesCode = getLanguagesCode();


        DataLists dataForNews=new DataLists(new ArrayList<>(categories.keySet()));

        DataForNewsWithCategory data=new DataForNewsWithCategory(user.getNumberOfArticles(),user.getUserID(),user.getOption(),languagesCode,dataForNews);

        ReturnData returnData=newsAccessor.getLatestListNewsFromCategories(data);


        getListNews(returnData);

    }
    
    public void getListNews(ReturnData data) throws JsonMappingException, JsonProcessingException{

//            objectMapper.registerModule(new JavaTimeModule());
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//
//            if(data.getArticle() == null){
//                log.error("results is null");
//                return;
//            }
//
//            Map<String,List<String>> categories=categoryService.myCategories();
//
//
//            List<Article> results=new LinkedList<>();
//            data.getArticle().forEach(a->{
//                try {
//                    ArticleResults art= objectMapper.readValue(a,ArticleResults.class);
//                    if(art!=null)
//                        results.addAll(art.getResults());
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//
//
//            });


           // List<ArticleReturn> articlesReturn=results.stream().map(ArticleReturn::new).toList();
        List<ArticleReturn> articlesReturn= getArticleType(data.getArticle());
        Map<String,List<String>> categories=categoryService.myCategories();

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

        data.getArticles().forEach(s -> articleToSend.append(s.toString()).append("\n"));

        NotificationData mail=new NotificationData()
                .setName(user.getName())
                .setSubject("Articles from newsAI")
                .setText(articleToSend.toString());


        sendToUser(data, mail, user);


    }

    private void sendToUser(ArticleFromLLm data, NotificationData mail, User user) throws JsonProcessingException {
        log.info("sendToUser for {} send in {}",user.getName(),data.getOption());
        switch (data.getOption()){

            case SendOption.EMAIL:
                mail.setConnectInfo(user.getEmail());
                producer.send(mail, KafkaTopic.SEND_EMAIL);
                break;

            case SendOption.SMS:
                mail.setConnectInfo(user.getPhone());
                producer.send(mail, KafkaTopic.SEND_SMS);

                break;

            case SendOption.WHATSAPP:
                mail.setConnectInfo(user.getPhone());
                producer.send(mail, KafkaTopic.SEND_WHATSAPP);
                break;

        }

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

    private List<String> getLanguagesCode() {

        return languageService.getLanguagesCode();

    }

    private List<ArticleReturn> getArticleType(List<String> articles){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        if(articles == null){
            log.error("results is null");
            return null;
        }

        List<Article> results=new LinkedList<>();
        articles.forEach(a->{
            try {
                ArticleResults art= objectMapper.readValue(a,ArticleResults.class);
                if(art!=null)
                    results.addAll(art.getResults());
            } catch (JsonProcessingException e) {
                log.error(e);
            }


        });


        return results.stream().map(ArticleReturn::new).toList();
    }
}

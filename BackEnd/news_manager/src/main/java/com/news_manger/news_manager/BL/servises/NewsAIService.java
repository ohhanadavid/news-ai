package com.news_manger.news_manager.BL.servises;

import java.util.*;

import com.news_manger.news_manager.BL.IChecking;
import com.news_manger.news_manager.DAL.articals.*;
import com.news_manger.news_manager.DAL.articalsToGet.*;
import com.news_manger.news_manager.DAL.notification.NotificationData;
import com.news_manger.news_manager.DAL.user.SendOption;
import com.news_manger.news_manager.DAL.user.UserRequest;
import com.news_manger.news_manager.DAL.user.UserRequestWithCategory;
import com.news_manger.news_manager.kafka.KafkaTopic;
import com.news_manger.news_manager.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news_manger.news_manager.DAL.user.User;

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
    private NewsAIAccessorService newsAccessor;


    
    public void getLatestNews(UserRequest user ) throws JsonProcessingException {
        log.info("getLatestNews");
        List<String> languagesCode = getLanguagesCode(user.getUserID());

        DataForNews data=new DataForNews(user.getNumberOfArticles(),user.getUserID(),user.getOption(),languagesCode);

        ReturnData returnData=newsAccessor.getLatestNews(data);

        getListNews(returnData);



        

    }

    public void getLatestNewsByCategory(UserRequestWithCategory user) throws JsonProcessingException {
        log.info("getLatestNewsByCategory");

        List<String> languagesCode = getLanguagesCode(user.getUserID());

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

        List<String> languagesCode = getLanguagesCode(user.getUserID());


        DataLists dataForNews=new DataLists(new ArrayList<>(categories.keySet()));

        DataForNewsWithCategory data=new DataForNewsWithCategory(user.getNumberOfArticles(),user.getUserID(),user.getOption(),languagesCode,dataForNews);

        ReturnData returnData=newsAccessor.getLatestListNewsFromCategories(data);


        getListNews(returnData);

    }



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

    private List<String> getLanguagesCode(String userId) {

        return languageService.getLanguagesCode();

    }


}

package com.news_manger.news_manager.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_manger.news_manager.BL.servises.NewsAIService;

import com.news_manger.news_manager.DAL.articals.ArticleFromLLm;
import com.news_manger.news_manager.DAL.articalsToGet.ReturnData;
import com.news_manger.news_manager.DAL.user.UserRequest;
import com.news_manger.news_manager.DAL.user.UserRequestWithCategory;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;

@Component
@Log4j2
public class Consumer {

    @Autowired
    ObjectMapper om;
    @Autowired
    private NewsAIService newsDataService;



    @KafkaListener(topics = {"getLatestNews"})
    @Async
    public void getLatestNews(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        log.info("KafkaListener-getLatestNews");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            UserRequest data= om.readValue(message.toString(), UserRequest.class);
            newsDataService.getLatestNews(data);
        }


    }

    @KafkaListener(topics = {"getLatestNewsByCategory"})
    @Async
    public void getLatestNewsByCategory(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        log.info("KafkaListener-getLatestNewsByCategory");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            UserRequestWithCategory data= om.readValue(message.toString(), UserRequestWithCategory.class);
            newsDataService.getLatestNewsByCategory(data);
        }


    }

    @KafkaListener(topics = {"getLatestListNewsByCategories"})
    @Async
    public void getLatestListNewsByCategories(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        log.info("KafkaListener-getLatestListNewsByCategories");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            UserRequest data= om.readValue(message.toString(), UserRequest.class);
            newsDataService.getLatestListNewsFromCategories(data);
        }


    }


//    @KafkaListener(topics = {"getNews"})
//    public void getNews(ConsumerRecord<?, ?> record) throws IOException {
//        log.info("KafkaListener-getNews");
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            if (kafkaMessage.isPresent()) {
//
//                Object message = kafkaMessage.get();
//                Map<String,Object> data= om.readValue(message.toString(), Map.class);
//                newsDataService.getNews(data);
//            }
//
//
//    }


    @KafkaListener(topics = {"getListNews"})
    public void getListNews(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        log.info("KafkaListener-getListNews");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            ReturnData data= om.readValue(message.toString(), ReturnData.class);
            newsDataService.getListNews(data);
        }


    }



    @KafkaListener(topics = {"LlmAnswer"})
    public void getFilterNews( ConsumerRecord<?, ?> record) throws Exception {
        log.info("KafkaListener-LlmAnswer");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            log.info("return {}",message.toString());
            if (message instanceof LinkedHashMap) {
                String jsonString = om.writeValueAsString(message);
                log.info("jsonString {}",jsonString);
                ArticleFromLLm data = om.readValue(jsonString, ArticleFromLLm.class);
                newsDataService.getFilterNews(data);
            } else {
                log.error("Unexpected message type: {}", message.getClass());
            }

//            ArticleFromLLm data= om.readValue(message.toString(), ArticleFromLLm.class);
            //  newsDataService.getFilterNews(data);
       }


    }

}

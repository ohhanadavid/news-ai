package com.news_manger.news_manager.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_manger.news_manager.BL.NewsService;

import com.news_manger.news_manager.DAL.articals.ArticleFromLLm;
import com.news_manger.news_manager.DAL.articals.ArticleReturn;
import com.news_manger.news_manager.DAL.articalsToGet.ReturnData;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Log4j2
public class Consumer {

    @Autowired
    ObjectMapper om;
    @Autowired
    private NewsService newsDataService;




    @KafkaListener(topics = {"getNews"})
    public void getNews(ConsumerRecord<?, ?> record) throws IOException {
        log.info("KafkaListener-getNews");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();
                Map<String,Object> data= om.readValue(message.toString(), Map.class);
                newsDataService.getNews(data);
            }


    }


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

package com.newsdata.io_accessor.newsdata_io_accessor.kafka;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsdata.io_accessor.newsdata_io_accessor.BL.NewsDataService;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Log4j2
public class Consumer {

    @Autowired
    ObjectMapper om;
    @Autowired
    private NewsDataService newsDataService;

    //@PostMapping("/api.getLatestNews")
    @KafkaListener(topics = {"api.getLatestNews"})
    public void getLatestNews(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        try{
            log.info("getLatestNews");
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();
                Map<String,Object> data= om.readValue(message.toString(), Map.class);
                newsDataService.getLatestNews(data);
            }

        }catch(Exception e)
        {
            log.error(e.getMessage());
           throw e;
        }
    }

   // @PostMapping("/api.getLatestNewsByCategory")
   @KafkaListener(topics = {"api.getLatestNewsByCategory"})
    public void getLatestNewsByCategory(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        try{
            log.info("getLatestNewsByCategory");
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();
                Map<String, Object> data = om.readValue(message.toString(), Map.class);
                String category = (String) data.get("category");
                newsDataService.getLatestNewsFromTopic(category, data);
            }

        }catch(Exception e)
        {
            log.error(e.getMessage());
            throw e;
        }
    }

    //@PostMapping("/api.getLatestListNewsByCategories")
    @KafkaListener(topics = {"api.getLatestListNewsByCategories"})
    public void getLatestListNewsFromCategories(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        try{
            log.info("getLatestListNewsByCategories");
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();
                Map<String, Object> data = om.readValue(message.toString(), Map.class);
                newsDataService.getLatestListNewsFromCategories(data);
            }
        }catch(Exception e)
        {
            log.error(e.getMessage());
            throw e;
        }
    }
}

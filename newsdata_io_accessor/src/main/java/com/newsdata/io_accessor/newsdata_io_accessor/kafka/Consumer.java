package com.newsdata.io_accessor.newsdata_io_accessor.kafka;


import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsdata.io_accessor.newsdata_io_accessor.BL.NewsDataService;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.DataForNews;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.DataForNewsWithCategory;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.DataForNewsWithOneCategory;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@Log4j2
public class Consumer {

    @Autowired
    ObjectMapper om;
    @Autowired
    private NewsDataService newsDataService;


    @KafkaListener(topics = {"api.getLatestNews"})
    public void getLatestNews(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        try{
            log.info("getLatestNews");
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();
                DataForNews data= om.readValue(message.toString(), DataForNews.class);
                newsDataService.getLatestNews(data);
            }

        }catch(Exception e)
        {
            log.error(e.getMessage());
           throw e;
        }
    }


   @KafkaListener(topics = {"api.getLatestNewsByCategory"})
    public void getLatestNewsByCategory(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        try{
            log.info("getLatestNewsByCategory");
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();
                DataForNewsWithOneCategory data = om.readValue(message.toString(), DataForNewsWithOneCategory.class);
                String category =  data.getCategory();
                newsDataService.getLatestNewsFromTopic(category, data);
            }

        }catch(Exception e)
        {
            log.error(e.getMessage());
            throw e;
        }
    }


    @KafkaListener(topics = {"api.getLatestListNewsByCategories"})
    public void getLatestListNewsFromCategories(ConsumerRecord<?, ?> record) throws JsonProcessingException {
        try{
            log.info("getLatestListNewsByCategories");
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {

                Object message = kafkaMessage.get();
                DataForNewsWithCategory data = om.readValue(message.toString(), DataForNewsWithCategory.class);
                newsDataService.getLatestListNewsFromCategories(data);
            }
        }catch(Exception e)
        {
            log.error(e.getMessage());
            throw e;
        }
    }
}

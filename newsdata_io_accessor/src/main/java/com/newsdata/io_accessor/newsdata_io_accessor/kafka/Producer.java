package com.newsdata.io_accessor.newsdata_io_accessor.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class Producer {

    public static final String GET_NEWS_TOPIC = "getNews";
    public static final String GET_LIST_NEWS_TOPIC = "getListNews";
    @Autowired
    ObjectMapper om;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(Object message,String topic) throws JsonProcessingException {
        kafkaTemplate.send(topic, om.writeValueAsString(message));
    }

}

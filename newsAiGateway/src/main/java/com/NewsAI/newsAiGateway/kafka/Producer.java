package com.NewsAI.newsAiGateway.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Producer {



    @Autowired
    ObjectMapper om;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(Object message,KafkaTopic topic) throws JsonProcessingException {
        log.info("send kafka to {}",topic);
        kafkaTemplate.send(topic.getTopic(), om.writeValueAsString(message));
    }

}

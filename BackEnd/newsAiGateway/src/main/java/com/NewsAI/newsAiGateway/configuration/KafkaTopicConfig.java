package com.NewsAI.newsAiGateway.configuration;


import com.NewsAI.newsAiGateway.kafka.KafkaTopic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;


import java.util.HashMap;
import java.util.Map;

import static com.NewsAI.newsAiGateway.kafka.KafkaTopic.*;


@Configuration
public class KafkaTopicConfig {

    @Value( "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }


    @Bean
    public NewTopic getNewsTopic() {
        return createTopic(GET_LATEST_NEWS);
    }

    @Bean
    public NewTopic getNewsByCategoryTopic() {
        return createTopic(GET_LATEST_NEWS_BY_CATEGORY);
    }

    @Bean
    public NewTopic getListNewsByCategoriesTopic() {
        return createTopic(GET_LATEST_NEWS_BY_MY_CATEGORIES);
    }


    private NewTopic createTopic(KafkaTopic topic) {
        return new NewTopic(topic.getTopic(), 1, (short) 1);
    }
}

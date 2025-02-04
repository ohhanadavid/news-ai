package com.newsdata.io_accessor.newsdata_io_accessor.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static com.newsdata.io_accessor.newsdata_io_accessor.kafka.Producer.GET_NEWS_TOPIC;
import static com.newsdata.io_accessor.newsdata_io_accessor.kafka.Producer.GET_LIST_NEWS_TOPIC;

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
        return new NewTopic(GET_NEWS_TOPIC, 1, (short) 1);
    }
    @Bean
    public NewTopic getListNewsTopic() {
        return new NewTopic(GET_LIST_NEWS_TOPIC, 1, (short) 1);
    }
}

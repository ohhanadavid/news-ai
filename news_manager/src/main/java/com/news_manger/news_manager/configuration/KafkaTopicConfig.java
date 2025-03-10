package com.news_manger.news_manager.configuration;

import com.news_manger.news_manager.kafka.KafkaTopic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static com.news_manger.news_manager.kafka.KafkaTopic.*;

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
    public NewTopic sendEmail() {
        return createTopic(SEND_EMAIL);
    }

    @Bean
    public NewTopic sendSms() {
        return createTopic(SEND_SMS);
    }

    @Bean
    public NewTopic getMyArticleTopic() {
        return createTopic(GET_MY_ARTICLE);
    }

    private NewTopic createTopic(KafkaTopic topic) {
        return new NewTopic(topic.getTopic(), 1, (short) 1);
    }
}

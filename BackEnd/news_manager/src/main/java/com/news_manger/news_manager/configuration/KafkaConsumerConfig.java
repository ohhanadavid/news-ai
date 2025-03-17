package com.news_manger.news_manager.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Map<String,Object>> specialListenerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "special-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // הגדרת Default Type רק ל-Listener הספציפי
        JsonDeserializer<Map<String,Object>> deserializer = new JsonDeserializer<>(Map.class);
        deserializer.addTrustedPackages("*");

        ErrorHandlingDeserializer<Map<String, Object>> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(Map.class, false));

        return new ConcurrentKafkaListenerContainerFactory<>() {{
            setConsumerFactory(new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer));
        }};
    }
}

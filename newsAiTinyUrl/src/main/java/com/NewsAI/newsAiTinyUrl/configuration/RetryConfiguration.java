package com.NewsAI.newsAiTinyUrl.configuration;


import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetryConfiguration {

    @Bean
    public RetryRegistry retryConfig(){
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(java.time.Duration.ofSeconds(2))
                .retryExceptions(org.springframework.data.redis.RedisConnectionFailureException.class)
                .build();

        RetryRegistry registry = RetryRegistry.of(config);
        registry.retry("myRetry");
        return registry;
    }
}

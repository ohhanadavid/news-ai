package com.news_manger.news_manager.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

@Configuration
@PropertySource(value = "file:${user.dir}/.env", ignoreResourceNotFound = true)
@Log4j2
public class EnvConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        log.info("Current directory: {}",System.getProperty("user.dir"));
        PropertySourcesPlaceholderConfigurer configure = new PropertySourcesPlaceholderConfigurer();
        configure.setIgnoreResourceNotFound(true);
        File envFile = new File(".env");
        log.info("Does .env exist? {}",envFile.exists());
        if (envFile.exists()) {
            try (FileInputStream fis = new FileInputStream(envFile)) {
                Properties properties = new Properties();
                properties.load(fis);
                log.info(  properties.isEmpty());
                configure.setProperties(properties);
            } catch (Exception e) {
                log.error("Failed to load .env file", e);
            }
        }
        return configure;
    }

}

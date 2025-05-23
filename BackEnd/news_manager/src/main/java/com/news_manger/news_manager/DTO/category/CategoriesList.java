package com.news_manger.news_manager.DTO.category;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConfigurationProperties(prefix = "categories")
public class CategoriesList {
    private final List<String> categories;

    public CategoriesList(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }
}

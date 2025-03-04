package com.news_manger.news_manager.DAL.category;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


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

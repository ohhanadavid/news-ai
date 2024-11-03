package com.newsdata.io_accessor.newsdata_io_accessor.DAL.categories;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;





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

package com.newsdata.io_accessor.newsdata_io_accessor.DAL;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class DataForNewsWithOneCategory extends DataForNews {

    private String category;

    public DataForNewsWithOneCategory(int numberOfArticle, String user, List<String> languages, String category) {
        super(numberOfArticle,user,languages);
        this.category=category;
    }
}

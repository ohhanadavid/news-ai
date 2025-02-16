package com.newsdata.io_accessor.newsdata_io_accessor.DAL;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataForNewsWithCategory extends DataForNews {

    private DataLists dataForNews;

    public DataForNewsWithCategory(int numberOfArticle, String user, List<String> languages, DataLists dataForNews) {
        super(numberOfArticle,user,languages);
        this.dataForNews=new DataLists(dataForNews);
    }
}

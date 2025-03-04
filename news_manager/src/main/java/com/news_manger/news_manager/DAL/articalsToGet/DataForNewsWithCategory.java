package com.news_manger.news_manager.DAL.articalsToGet;

import com.news_manger.news_manager.DAL.articals.DataLists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class DataForNewsWithCategory extends DataForNews {

    private DataLists dataForNews;

    public DataForNewsWithCategory(int numberOfArticle, String user, List<String> languages, DataLists dataForNews) {
        super(numberOfArticle,user,languages);
        this.dataForNews=new DataLists(dataForNews);
    }
}

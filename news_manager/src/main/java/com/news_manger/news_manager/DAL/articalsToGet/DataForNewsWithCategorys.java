package com.news_manger.news_manager.DAL.articalsToGet;

import com.news_manger.news_manager.DAL.articals.DataLists;
import com.news_manger.news_manager.DAL.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class DataForNewsWithCategorys extends DataForNews {

    private DataLists dataForNews;

    public DataForNewsWithCategorys(int numberOfArticle, String user, List<String> languages, DataLists dataForNews) {
        super(numberOfArticle,user,languages);
        this.dataForNews=new DataLists(dataForNews);
    }
}

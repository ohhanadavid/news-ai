package com.news_manger.news_manager.DTO.articalsToGet;

import com.news_manger.news_manager.DTO.articals.DataLists;
import com.news_manger.news_manager.DTO.user.SendOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class DataForNewsWithCategory extends DataForNews {

    private DataLists dataForNews;

    public DataForNewsWithCategory(int numberOfArticle, String user, SendOption option, List<String> languages, DataLists dataForNews) {
        super(numberOfArticle,user,option,languages);
        this.dataForNews=new DataLists(dataForNews);
    }
}

package com.news_manger.news_manager.DAL.articalsToGet;


import com.news_manger.news_manager.DAL.user.SendOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DataForNews {

    protected Integer numberOfArticle;
    protected String to;
    protected SendOption option;
    private List<String> language;

    public DataForNews setData(DataForNews data){
        numberOfArticle=data.getNumberOfArticle();
        to= data.getTo();
        return this;
    }
}

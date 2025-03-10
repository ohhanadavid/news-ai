package com.news_manger.news_manager.DAL.articalsToGet;

import com.news_manger.news_manager.DAL.user.SendOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnData {
    private List<String> article;
    protected Integer numberOfArticle;
    protected String to;
    protected SendOption option;
    
    public ReturnData setData(ReturnData data){
        this.setTo(data.getTo());
        this.setOption(data.option);
        this.setNumberOfArticle(data.getNumberOfArticle());
        this.article = (article == null || article.isEmpty()) ? null : new ArrayList<>(data.getArticle());
        return this;
    }

    public ReturnData(DataForNews data,List<String> article){
        this.setTo(data.getTo());
        this.setOption(data.getOption());
        this.setNumberOfArticle(data.getNumberOfArticle());
        this.article=article == null ? null : new ArrayList<>(article);
    }



    public ReturnData(DataForNews data,String article){
        this.setTo(data.getTo());
        this.setOption(data.getOption());
        this.setNumberOfArticle(data.getNumberOfArticle());
        this.article = (article == null || article.isEmpty()) ? null : new ArrayList<>(List.of(article));
    }

}

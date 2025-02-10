package com.news_manger.news_manager.DAL.articalsToGet;

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
    
    public ReturnData setData(ReturnData data){
        this.setTo(data.getTo());
        this.setNumberOfArticle(data.getNumberOfArticle());
        this.article = (article == null || article.isEmpty()) ? null : new ArrayList<>(data.getArticle());
        return this;
    }



}

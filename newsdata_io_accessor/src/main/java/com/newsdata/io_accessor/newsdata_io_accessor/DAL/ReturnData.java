package com.newsdata.io_accessor.newsdata_io_accessor.DAL;

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




    public ReturnData(DataForNews data,List<String> article){
        this.setTo(data.getTo());
        this.setNumberOfArticle(data.getNumberOfArticle());
        this.article=article == null ? null : new ArrayList<>(article);
    }



    public ReturnData(DataForNews data,String article){
        this.setTo(data.getTo());
        this.setNumberOfArticle(data.getNumberOfArticle());
        this.article = (article == null || article.isEmpty()) ? null : new ArrayList<>(List.of(article));
    }
}

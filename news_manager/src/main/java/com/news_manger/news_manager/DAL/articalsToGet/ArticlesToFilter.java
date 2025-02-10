package com.news_manger.news_manager.DAL.articalsToGet;

import com.news_manger.news_manager.DAL.articals.ArticleReturn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ArticlesToFilter extends DataForNews {

    List<ArticleReturn> articleReturnList;
    Map<String,List<String>> preference;

    public ArticlesToFilter setData(DataForNews data){
        numberOfArticle=data.getNumberOfArticle();
        to= data.getTo();
        return this;
    }

    public ArticlesToFilter setData(ReturnData data){
        numberOfArticle=data.getNumberOfArticle();
        to= data.getTo();
        return this;
    }
}

package com.news_manger.news_manager.DTO.articals;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames=true)
public class ArticleResults {
    private String status;
    private int totalResults;
    List<Article> results;
    String nextPage;
}

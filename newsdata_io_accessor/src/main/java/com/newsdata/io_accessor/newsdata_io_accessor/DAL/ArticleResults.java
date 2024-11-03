package com.newsdata.io_accessor.newsdata_io_accessor.DAL;

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
}

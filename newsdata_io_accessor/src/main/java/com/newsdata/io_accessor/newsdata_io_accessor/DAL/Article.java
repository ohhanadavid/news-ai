package com.newsdata.io_accessor.newsdata_io_accessor.DAL;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString(includeFieldNames=true)

public class Article {
    private  String article_id;
    private String title;
    private String link;
    private  List<String> keywords;
    private List<String> creator;
    private String video_url ;
    private String description;
    private String  content ;
    @JsonDeserialize(using= ArticalDataTimeFormatter.class)
    private LocalDateTime pubDate ;
    private String image_url ;
    private String source_id ;
    private int source_priority ;
    private String source_url ;
    private String source_icon ;
    private String language ;
    private List<String> country ;
    private  List<String> category ; 
    private String ai_tag ;
    private String sentiment ;
    private String sentiment_stats;
    private String ai_region;
    private String ai_org ;
}

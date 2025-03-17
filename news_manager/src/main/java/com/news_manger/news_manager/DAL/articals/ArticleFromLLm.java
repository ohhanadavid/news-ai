package com.news_manger.news_manager.DAL.articals;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.news_manger.news_manager.DAL.user.SendOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleFromLLm {

    private String to;
    private SendOption option;
     @JsonDeserialize(using = ArticlesDeserializer.class)
    private ArrayList<ArticleReturnFromLLM> articles;

}

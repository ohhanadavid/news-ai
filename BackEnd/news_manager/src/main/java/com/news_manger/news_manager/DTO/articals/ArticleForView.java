package com.news_manger.news_manager.DTO.articals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleForView {
    private String title;
    private String url;
}

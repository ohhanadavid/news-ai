package com.news_manger.news_manager.DTO.articalsToGet;

import com.news_manger.news_manager.DTO.user.SendOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class DataForNewsWithOneCategory extends DataForNews {

    private String category;

    public DataForNewsWithOneCategory(int numberOfArticle, String user, SendOption option, List<String> languages, String category) {
        super(numberOfArticle,user,option,languages);
        this.category=category;
    }
}

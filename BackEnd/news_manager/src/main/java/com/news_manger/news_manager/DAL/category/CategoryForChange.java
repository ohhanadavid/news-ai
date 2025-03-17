package com.news_manger.news_manager.DAL.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryForChange {
    private Category oldCategory;
    private Category newCategory;
    

}

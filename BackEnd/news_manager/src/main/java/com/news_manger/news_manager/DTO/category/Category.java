package com.news_manger.news_manager.DTO.category;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    
    private  CategoryKey category;

}

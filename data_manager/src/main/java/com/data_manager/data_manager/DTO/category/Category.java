package com.data_manager.data_manager.DTO.category;


import com.data_manager.data_manager.DAL.modol.category.CategoryToDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    
    private CategoryKeyFromUser category;

    public CategoryToDB toCategoryToDB(String userId){
        return new CategoryToDB(category.toCategoryKeyForDB(userId));
    }

}

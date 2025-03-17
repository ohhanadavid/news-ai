package com.data_manager.data_manager.DTO.category;



import com.data_manager.data_manager.DAL.modol.category.CategoryKeyForDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(includeFieldNames=true)
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor

public class CategoryKeyFromUser {

    private String preference;
    private String category;

    public CategoryKeyForDB toCategoryKeyForDB (String userID){
        return  new CategoryKeyForDB(userID,preference,category);
    }

}

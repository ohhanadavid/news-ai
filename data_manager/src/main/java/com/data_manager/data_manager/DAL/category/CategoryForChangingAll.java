package com.data_manager.data_manager.DAL.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryForChangingAll {
    private String email;

    private String oldCategory;

    private String newCategory;

    private String oldPreference;

    private String newPreference;
    

}

package com.data_manager.data_manager.DTO.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryForChangingAll {


    private String oldCategory;

    private String newCategory;

    private String oldPreference;

    private String newPreference;
    

}

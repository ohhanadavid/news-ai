package com.user_accessor.user_accessor.DAL.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryForChange {
    private Category oldCategory;
    private Category newCategory;
    

}

package com.user_accessor.user_accessor.DAL.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryForChange {
    private String oldCategory;
    private String newCategory;
}

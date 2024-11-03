package com.data_manager.data_manager.DAL.category;



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

public class CategoryKey {
    
    
    private String email;
    
    private String preferencec;
   
    private String category;
}

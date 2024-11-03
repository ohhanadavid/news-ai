package com.data_manager.data_manager.DAL.languege;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(includeFieldNames=true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)

public class LanguageKey {
    
    
    @NonNull
    private String email;
    
    @NonNull
    private String language;
    

}

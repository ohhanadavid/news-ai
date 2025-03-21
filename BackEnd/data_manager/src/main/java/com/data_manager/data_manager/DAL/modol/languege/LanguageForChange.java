package com.data_manager.data_manager.DAL.modol.languege;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LanguageForChange {
    private String oldLanguage;
    private String newLanguage;
    private String newLanguageCode;


}

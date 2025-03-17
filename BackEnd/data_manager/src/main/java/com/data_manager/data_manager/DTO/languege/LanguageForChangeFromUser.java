package com.data_manager.data_manager.DTO.languege;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageForChangeFromUser {
    private String oldLanguage;
    private String newLanguage;


}

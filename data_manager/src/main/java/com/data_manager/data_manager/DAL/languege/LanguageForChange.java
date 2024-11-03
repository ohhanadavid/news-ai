package com.data_manager.data_manager.DAL.languege;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageForChange {
    private LangaugeUser oldLanguage;
    private LangaugeUser newLanguage;


}

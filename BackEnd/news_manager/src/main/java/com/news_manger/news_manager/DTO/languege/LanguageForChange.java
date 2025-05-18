package com.news_manger.news_manager.DTO.languege;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageForChange {
    private LangaugeUser oldLanguage;
    private LangaugeUser newLanguage;


}

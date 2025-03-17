package com.news_manger.news_manager.DAL.languege;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)

@NoArgsConstructor
@AllArgsConstructor

public class Language {
    
    LanguageKey languageKey;
    
    private String languageCode;

    public Language(LangaugeUser fromUser,String code){
        this.languageCode=code;
        this.languageKey=new LanguageKey(fromUser.getEmail(), fromUser.getLanguage());
    }
    public Language(LangaugeUser fromUser){
        this.languageCode="";
        this.languageKey=new LanguageKey(fromUser.getEmail(), fromUser.getLanguage());
    }

}

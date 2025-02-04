package com.data_manager.data_manager.DAL.languege;


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

    public Language(LanguageUser fromUser, String code){
        this.languageCode=code;
        this.languageKey=new LanguageKey(fromUser.getEmail(), fromUser.getLanguage());
    }
    public Language(LanguageUser fromUser){
        this.languageCode="";
        this.languageKey=new LanguageKey(fromUser.getEmail(), fromUser.getLanguage());
    }

}

package com.data_manager.data_manager.DTO.languege;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)

@NoArgsConstructor
@AllArgsConstructor

public class Language {

    @NonNull
    private String language;
    private String languageCode;


    public Language(LanguageUser fromUser){
        this.languageCode="";
        this.language= fromUser.getLanguage();
    }

}

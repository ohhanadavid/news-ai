package com.user_accessor.user_accessor.DAL.languege;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageForChange {
    private Language oldLanguage;
    private Language newLanguage;


}

package com.data_manager.data_manager.DAL.languege;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class LanguagesForChangeToSend {
    private String oldLanguage;
    private String newLanguage;
    private String newLanguageCode;
}

package com.news_manger.news_manager.DTO.languege;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LangaugesForSend {
    private Language oldLanguage;
    private Language newLanguage;
}

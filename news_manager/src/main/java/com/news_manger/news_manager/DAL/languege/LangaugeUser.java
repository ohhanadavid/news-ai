package com.news_manger.news_manager.DAL.languege;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class LangaugeUser {

    private String email;
    private String language;

}

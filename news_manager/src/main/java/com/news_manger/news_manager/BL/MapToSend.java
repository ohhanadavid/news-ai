package com.news_manger.news_manager.BL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapToSend {
    Map<String,List<String>> data;

    public Map<String,List<String>> detData(){
        return data;
    }
}

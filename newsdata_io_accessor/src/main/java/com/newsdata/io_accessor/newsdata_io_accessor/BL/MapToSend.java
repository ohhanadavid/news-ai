package com.newsdata.io_accessor.newsdata_io_accessor.BL;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapToSend {
    Map<String,List<String>> data;

    public Map<String,List<String>> detData(){
        return data;
    }
}

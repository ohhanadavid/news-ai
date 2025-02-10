package com.newsdata.io_accessor.newsdata_io_accessor.DAL;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataForNews {

    private Integer numberOfArticle;
    private String to;
    private List<String> language;
}

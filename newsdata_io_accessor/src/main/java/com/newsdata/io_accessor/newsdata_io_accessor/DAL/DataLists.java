package com.newsdata.io_accessor.newsdata_io_accessor.DAL;

import java.util.List;

import lombok.Data;
import lombok.ToString;


@Data
@ToString(includeFieldNames=true)
public class DataLists {
    private List<String> categories;
    private List<String> language;

}

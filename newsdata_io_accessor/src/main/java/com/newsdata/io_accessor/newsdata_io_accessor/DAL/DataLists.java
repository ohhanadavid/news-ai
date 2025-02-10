package com.newsdata.io_accessor.newsdata_io_accessor.DAL;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;


@Data
@ToString(includeFieldNames=true)
public class DataLists {
    private List<String> categories;


    public DataLists(DataLists original) {
        // Create new ArrayList and copy all elements
        this.categories = original.categories == null
                ? null
                : new ArrayList<>(original.categories);


    }

}

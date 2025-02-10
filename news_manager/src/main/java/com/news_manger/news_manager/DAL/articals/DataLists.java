package com.news_manger.news_manager.DAL.articals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString(includeFieldNames=true)
@NoArgsConstructor
@AllArgsConstructor
public class DataLists implements Serializable {
    private List<String> categories;


    public DataLists(DataLists original) {
        // Create new ArrayList and copy all elements
        this.categories = original.categories == null
                ? null
                : new ArrayList<>(original.categories);


    }

}

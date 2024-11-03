package com.news_manger.news_manager.DAL.articals;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString(includeFieldNames=true)
@NoArgsConstructor
@AllArgsConstructor
public class DataLists {
    private List<String> categories;
    private List<String> language;

}

package com.news_manger.news_manager.BL.servises;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ICategoryService {
    public Map<String, List<String>> myCategories ();
    public List<String> getPreferenceByCategory( String category);
}

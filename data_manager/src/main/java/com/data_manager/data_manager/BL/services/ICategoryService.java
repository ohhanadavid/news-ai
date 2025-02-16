package com.data_manager.data_manager.BL.services;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ICategoryService {
    public Map<String,List<String>> myCategories (String email);
    public List<String> getPreferencecByCategory (String email, String category);
}

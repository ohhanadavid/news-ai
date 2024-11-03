package com.news_manger.news_manager.BL;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ICategoryService {
    public ResponseEntity<?> myCategories (String email);
    public ResponseEntity<?> getPreferencecByCategory (String email,String category);
}

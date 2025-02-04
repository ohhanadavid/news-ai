package com.user_accessor.user_accessor;

import com.user_accessor.user_accessor.BL.CategoryService;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.log4j.Log4j2;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.user_accessor.user_accessor.DAL.category.Category;
import com.user_accessor.user_accessor.DAL.category.CategoryRepository;

@Log4j2
@SpringBootTest
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategory() {
        Category category = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.saveCategory(category);

        assertNotNull(result);
        verify(categoryRepository).save(category);
    }

    @Test
    void testGetPreferencesByCategory() {
        String email = "test@test.com";
        String category = "movies";
        List<String> preferences = Arrays.asList("action", "comedy");

        when(categoryRepository.getPreferenceByCategory(email, category)).thenReturn(preferences);

        List<String> result = categoryService.getPreferencesByCategory(email, category);

        assertEquals(preferences, result);
        verify(categoryRepository).getPreferenceByCategory(email, category);
    }

    @Test
    void testDeleteCategory() {
        String email = "test@test.com";
        String category = "movies";

        categoryService.deleteCategory(email, category);

        verify(categoryRepository).deleteCategory(email, category);
    }

    @Test
    void testUpdateCategory() {
        String oldCategory = "movies";
        String newCategory = "films";
        String email = "test@test.com";

        categoryService.updateCategory(oldCategory, newCategory, email);

        verify(categoryRepository).updateCategory(oldCategory, newCategory, email);
    }
}


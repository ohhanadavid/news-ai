//import com.data_manager.data_manager.BL.controller.CategoryController;
//import com.data_manager.data_manager.BL.services.CategoryService;
//import com.data_manager.data_manager.DTO.category.Category;
//import com.data_manager.data_manager.DTO.category.CategoryForChange;
//import com.data_manager.data_manager.DTO.category.CategoryKeyFromUser;
//import com.data_manager.data_manager.DTO.category.PreferenceForChange;
//import com.data_manager.data_manager.DTO.user.UserData;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//class CategoryControllerTest {
//
//    @Mock
//    private CategoryService categoryService;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private CategoryController categoryController;
//
//    private UserData testUser;
//    private String newsAiAccessorUrl = "http://test-url.com/";
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        testUser = new UserData(
//
//        );
//        testUser.setUserID("test-user-123");
//
//        // Set the newsAiAccessorUrl field using reflection since it might be private/final
//        try {
//            java.lang.reflect.Field field = categoryController.getClass().getDeclaredField("newsAiAccessorUrl");
//            field.setAccessible(true);
//            field.set(categoryController, newsAiAccessorUrl);
//        } catch (Exception e) {
//            // Handle reflection exception
//        }
//    }
//
//    @Test
//    void testSaveCategory_Success() {
//        // Arrange
//        Category category = new Category(new CategoryKeyFromUser("preference1", "category1"));
//
//        // Configure mock responses
//        doNothing().when(categoryService).saveCategory(any());
//        when(restTemplate.getForObject(contains("api.checkCategory"), eq(Boolean.class))).thenReturn(true);
//
//        // Act
//        String result = categoryController.saveCategory(category, testUser);
//
//        // Assert
//        assertEquals("Category Saved!", result);
//        verify(categoryService, times(1)).saveCategory(any());
//    }
//
//    @Test
//    void testSaveCategory_InvalidCategory() {
//        // Arrange
//        Category category = new Category(new CategoryKeyFromUser("preference1", "invalid-category"));
//
//        // Configure mock to return false for invalid category
//        when(restTemplate.getForObject(contains("api.checkCategory"), eq(Boolean.class))).thenReturn(false);
//
//        // Act & Assert
//        HttpServerErrorException exception = assertThrows(
//                HttpServerErrorException.class,
//                () -> categoryController.saveCategory(category, testUser)
//        );
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
//        verify(categoryService, never()).saveCategory(any());
//    }
//
//    @Test
//    void testSaveCategory_DuplicatePreference() {
//        // Arrange
//        Category category = new Category(new CategoryKeyFromUser("existing-preference", "category1"));
//
//        // Configure mocks
//        when(restTemplate.getForObject(contains("api.checkCategory"), eq(Boolean.class))).thenReturn(true);
//        doThrow(new HttpClientErrorException(HttpStatus.CONFLICT))
//                .when(categoryService).checkPreference(any());
//
//        // Act & Assert
//        HttpClientErrorException exception = assertThrows(
//                HttpClientErrorException.class,
//                () -> categoryController.saveCategory(category, testUser)
//        );
//        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
//        verify(categoryService, never()).saveCategory(any());
//    }
//
//    @Test
//    void testGetPreferenceByCategory() {
//        // Arrange
//        String categoryName = "category1";
//        List<String> expectedPreferences = Arrays.asList("pref1", "pref2", "pref3");
//        when(categoryService.getPreferencesByCategory(testUser.getUserID(), categoryName))
//                .thenReturn(expectedPreferences);
//
//        // Act
//        List<String> result = categoryController.getPreferenceByCategory(categoryName, testUser);
//
//        // Assert
//        assertEquals(expectedPreferences, result);
//        verify(categoryService).getPreferencesByCategory(testUser.getUserID(), categoryName);
//    }
//
//    @Test
//    void testMyCategories() {
//        // Arrange
//        Map<String, List<String>> expectedCategories = new HashMap<>();
//        expectedCategories.put("category1", Arrays.asList("pref1", "pref2"));
//        expectedCategories.put("category2", Arrays.asList("pref3", "pref4"));
//
//        when(categoryService.myCategories(testUser.getUserID())).thenReturn(expectedCategories);
//
//        // Act
//        Map<String, List<String>> result = categoryController.myCategories(testUser);
//
//        // Assert
//        assertEquals(expectedCategories, result);
//        verify(categoryService).myCategories(testUser.getUserID());
//    }
//
//    @Test
//    void testDeletePreference() {
//        // Arrange
//        Category category = new Category(new CategoryKeyFromUser("preference1", "category1"));
//        doNothing().when(categoryService).deletePreferences(any());
//
//        // Act
//        String result = categoryController.deletePreference(category, testUser);
//
//        // Assert
//        assertEquals("preference deleted!", result);
//        verify(categoryService).deletePreferences(any());
//    }
//
//    @Test
//    void testDeleteCategory() {
//        // Arrange
//        String categoryName = "category1";
//        doNothing().when(categoryService).deleteCategory(testUser.getUserID(), categoryName);
//
//        // Act
//        String result = categoryController.deleteCategory(categoryName, testUser);
//
//        // Assert
//        assertEquals("category deleted!", result);
//        verify(categoryService).deleteCategory(testUser.getUserID(), categoryName);
//    }
//
//    @Test
//    void testUpdateCategory_Success() {
//        // Arrange
//        CategoryForChange category = new CategoryForChange();
//        category.setOldCategory("oldCategory");
//        category.setNewCategory("newCategory");
//
//        Map<String, List<String>> categories = new HashMap<>();
//        categories.put("oldCategory", Arrays.asList("pref1", "pref2"));
//
//        when(categoryService.myCategories(testUser.getUserID())).thenReturn(categories);
//        when(restTemplate.getForObject(contains("api.checkCategory"), eq(Boolean.class))).thenReturn(true);
//        doNothing().when(categoryService).updateCategory(anyString(), anyString(), anyString());
//
//        // Act
//        String result = categoryController.updateCategory(category, testUser);
//
//        // Assert
//        assertEquals("category updated!", result);
//        verify(categoryService).updateCategory("oldCategory", "newCategory", testUser.getUserID());
//    }
//
//    @Test
//    void testUpdateCategory_CategoryNotFound() {
//        // Arrange
//        CategoryForChange category = new CategoryForChange();
//        category.setOldCategory("nonExistentCategory");
//        category.setNewCategory("newCategory");
//
//        Map<String, List<String>> categories = new HashMap<>();
//        categories.put("differentCategory", Arrays.asList("pref1", "pref2"));
//
//        when(categoryService.myCategories(testUser.getUserID())).thenReturn(categories);
//        when(restTemplate.getForObject(contains("api.checkCategory"), eq(Boolean.class))).thenReturn(true);
//
//        // Act & Assert
//        HttpClientErrorException exception = assertThrows(
//                HttpClientErrorException.class,
//                () -> categoryController.updateCategory(category, testUser)
//        );
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
//        verify(categoryService, never()).updateCategory(anyString(), anyString(), anyString());
//    }
//
//    @Test
//    void testUpdateCategory_WithExistingNewCategory() {
//        // Arrange
//        CategoryForChange category = new CategoryForChange();
//        category.setOldCategory("oldCategory");
//        category.setNewCategory("existingCategory");
//
//        Map<String, List<String>> categories = new HashMap<>();
//        categories.put("oldCategory", Arrays.asList("pref1", "pref2"));
//        categories.put("existingCategory", Arrays.asList("pref1", "pref3"));
//
//        when(categoryService.myCategories(testUser.getUserID())).thenReturn(categories);
//        when(restTemplate.getForObject(contains("api.checkCategory"), eq(Boolean.class))).thenReturn(true);
//        doNothing().when(categoryService).updateCategory(anyString(), anyString(), anyString());
//
//        // Act
//        String result = categoryController.updateCategory(category, testUser);
//
//        // Assert
//        assertEquals("category updated!", result);
//        // Should call deletePreference for the common preference "pref1"
//        verify(categoryService).updateCategory("oldCategory", "existingCategory", testUser.getUserID());
//    }
//
//    @Test
//    void testUpdatePreference_Success() {
//        // Arrange
//        PreferenceForChange preference = new PreferenceForChange();
//        preference.setOldPreference("oldPref");
//        preference.setNewPreference("newPref");
//        preference.setCategory("category1");
//
//        doNothing().when(categoryService).updatePreference(any(), anyString());
//
//        // Act
//        String result = categoryController.updatePreference(preference, testUser);
//
//        // Assert
//        assertEquals("Preference update", result);
//        verify(categoryService).updatePreference(preference, testUser.getUserID());
//    }
//
//    @Test
//    void testUpdatePreference_DuplicatePreference() {
//        // Arrange
//        PreferenceForChange preference = new PreferenceForChange();
//        preference.setOldPreference("oldPref");
//        preference.setNewPreference("existingPref");
//        preference.setCategory("category1");
//
//        doThrow(new HttpClientErrorException(HttpStatus.CONFLICT))
//                .when(categoryService).checkPreference(any());
//
//        // Act & Assert
//        HttpClientErrorException exception = assertThrows(
//                HttpClientErrorException.class,
//                () -> categoryController.updatePreference(preference, testUser)
//        );
//        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
//        verify(categoryService, never()).updatePreference(any(), anyString());
//    }
//}
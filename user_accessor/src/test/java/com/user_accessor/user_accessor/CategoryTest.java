package com.user_accessor.user_accessor;

import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class CategoryTest {
    // @Autowired
    // CategoryController categoryController;

    // Category c1 = new Category(new CategoryKey("sustujbv167@gmail.com", "a", "sports"));
    // Category c2 = new Category(new CategoryKey("sustujbv167@gmail.com", "b", "sports"));
    // Category c3 = new Category(new CategoryKey("sustujbv167@gmail.com", "c", "sports"));
    // Category c4 = new Category(new CategoryKey("sustujbv167@gmail.com", "d", "food"));
    // Category c5 = new Category(new CategoryKey("sustujbv167@gmail.com", "e", "food"));
    // Category c6 = new Category(new CategoryKey("sustujbv167@gmail.com", "f", "top"));

    // Category c7 = new Category(new CategoryKey("sustujbv168@gmail.com", "a", "sports"));
    // Category c8 = new Category(new CategoryKey("sustujbv168@gmail.com", "b", "sports"));
    // Category c9 = new Category(new CategoryKey("sustujbv168@gmail.com", "c", "sports"));
    // Category c10 = new Category(new CategoryKey("sustujbv168@gmail.com", "d", "politics"));
    // Category c11 = new Category(new CategoryKey("sustujbv168@gmail.com", "e", "politics"));
    // Category c12 = new Category(new CategoryKey("sustujbv168@gmail.com", "f", "politics"));
    // Category c13 = new Category(new CategoryKey("sustujbv168@gmail.com", "g", "politics"));

    // Category c14 = new Category(new CategoryKey("sustujbv169@gmail.com", "a", "top"));
    // Category c15 = new Category(new CategoryKey("sustujbv169@gmail.com", "b", "top"));
    // Category c16 = new Category(new CategoryKey("sustujbv169@gmail.com", "c", "sports"));
    // Category c17 = new Category(new CategoryKey("sustujbv169@gmail.com", "d", "sports"));

    // Category c18 = new Category(new CategoryKey("sustujbv16@gmail.com", "succer", "sports"));


    // @Test
    // public void saveCategory (){
    //     log.info("Save Category");
    //     // assertEquals(categoryController.saveCategory(c1).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c2).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c3).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c4).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c5).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c7).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c8).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c9).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c10).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c11).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c12).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c14).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c15).getStatusCode(), HttpStatus.valueOf(200));
    //     // assertEquals(categoryController.saveCategory(c16).getStatusCode(), HttpStatus.valueOf(200));



    // }
    // @Test
    // public void getPreferencecByCategory (){
    //     log.info("getPreferencecByCategory");
    //     List<String> c = new LinkedList<>();
    //     c.add("a");
    //     c.add("b");
    //     c.add("c");
    //     assertEquals(categoryController.getPreferencecByCategory("sustujbv167@gmail.com", "sports").getBody(), c);
        
    // }
    // @Test
    // public void myCategories (){
    //     log.info("get Category");    
    //     Map<String,List<String>> categoryResult= (Map<String, List<String>>) categoryController.myCategories("sustujbv167@gmail.com").getBody();
    //     Map<String,List<String>> c = new HashMap<>();
    //     c.put("sports",List.of("a","b","c") );
    //     c.put("food",List.of("d","e") );
        
    //     assertEquals(categoryResult, c);

    // }
    // @Test
    // public void deletePreferencec (){
    //     log.info("delete Preferencec");
    //     categoryController.deletePreferencec(c1);
    //     List<String> c = new LinkedList<>();
    //     c.add("b");
    //     c.add("c");
    //     assertEquals(categoryController.getPreferencecByCategory("sustujbv167@gmail.com", "sports").getBody(), c);
       
    // }

    // @Test
    // public void deleteCategory (){
    //     log.info("delete Preferencec");

    //     categoryController.deleteCategory("sustujbv169@gmail.com", "top");
    //     Map<String,List<String>> categoryResult= (Map<String, List<String>>) categoryController.myCategories("sustujbv169@gmail.com").getBody();
    //     Map<String,List<String>> c = new HashMap<>();
    //     c.put("sports",List.of("c") );
        
        
    //     assertEquals(categoryResult, c);

    // }
    // @Test
    // public void updateCategory(){
    //     log.info("updateCategory");
    //     categoryController.updateCategory("food", "dinner", "sustujbv167@gmail.com");
    //     Map<String,List<String>> categoryResult= (Map<String, List<String>>) categoryController.myCategories("sustujbv167@gmail.com").getBody();
    //     Map<String,List<String>> c = new HashMap<>();
    //     c.put("sports",List.of("b","c") );
    //     c.put("dinner",List.of("d","e") );
        
    //     assertEquals(categoryResult, c);
      
    // }
    // @Test
    // public void updatePreferencec(){
    //     log.info("updatePreferencec");
        
        
    //         categoryController.updatePreferencec("b", "newPreferencec", "sustujbv167@gmail.com", "sports");
    //         List<String> c = new LinkedList<>();
    //         c.add("newPreferencec");
    //         c.add("c");
    //         assertEquals(categoryController.getPreferencecByCategory("sustujbv167@gmail.com", "sports").getBody(), c);
    // }
    // @Test
    // public void updateAll( ){
    //     log.info("updateAll");
          
     
    //     // categoryController.updateAll(new CategoryForChange(c16, c16));
    //     // List<String> c = new LinkedList<>();
    //     // c.add("d");
    //     // assertEquals(categoryController.getPreferencecByCategory("sustujbv169@gmail.com", "sports").getBody(), c);
    //      assertEquals(categoryController.updateAll(new CategoryForChange(c16, c18)).getStatusCode(), HttpStatus.valueOf(400));
        
        
            
     
    // }

}

package com.user_accessor.user_accessor;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.user_accessor.user_accessor.BL.LanguegeController;
import com.user_accessor.user_accessor.DAL.languege.Language;
import com.user_accessor.user_accessor.DAL.languege.LanguageForChange;
import com.user_accessor.user_accessor.DAL.languege.LanguageKey;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class LanguageTest {
//     @Autowired
//     LanguegeController langCollectors;
     
//     Language l1 = new Language(new LanguageKey("sustujbv173@gmail.com", "French"), "fr");
//     Language l2 = new Language(new LanguageKey("sustujbv173@gmail.com", "Hebrew"), "he");
//     Language l3 = new Language(new LanguageKey("sustujbv173@gmail.com", "Kinyarwanda"), "rw");
//     Language l4 = new Language(new LanguageKey("sustujbv173@gmail.com", "Oriya"), "or");

//     Language l5 = new Language(new LanguageKey("sustujbv178@gmail.com", "French"), "fr");
//     Language l6 = new Language(new LanguageKey("sustujbv178@gmail.com", "Norwegian"), "no");

//     Language l7 = new Language(new LanguageKey("sustujbv17@gmail.com", "French"), "fr");

//     @Test
//     public void saveLanguege(){
//         log.info("Save languege");
//         assertEquals(langCollectors.saveLanguege(l1).getStatusCode(), HttpStatus.valueOf(200));
//         assertEquals(langCollectors.saveLanguege(l2).getStatusCode(), HttpStatus.valueOf(200));
//         assertEquals(langCollectors.saveLanguege(l3).getStatusCode(), HttpStatus.valueOf(200));
        
//         assertEquals(langCollectors.saveLanguege(l5).getStatusCode(), HttpStatus.valueOf(200));
//         assertEquals(langCollectors.saveLanguege(l6).getStatusCode(), HttpStatus.valueOf(200));
        
//         assertEquals(langCollectors.saveLanguege(l1).getStatusCode(), HttpStatus.valueOf(400));
//         assertEquals(langCollectors.saveLanguege(l7).getStatusCode(), HttpStatus.valueOf(400));

//     }
//    @Test
//     public void getLangueges(){    
//         log.info("get langueges");
//         List<String> l=new LinkedList<>();
//         l.add("French");
//         l.add("Hebrew");
//         l.add("Kinyarwanda");
//         assertEquals(langCollectors.getLangueges("sustujbv173@gmail.com").getBody(), l);
        
      
//     }
//     @Test
//     public void getLanguegesCode(){  
//         log.info("get langueges code");
//         List<String> l=new LinkedList<>();
//         l.add("fr");
//         l.add("he");
//         l.add("rw");
//         assertEquals(langCollectors.getLanguegesCode("sustujbv173@gmail.com").getBody(), l);
//     }
//     @Test
//     public void deleteLanguege(){
//         log.info("delete language");
        
//         assertEquals(langCollectors.deleteLanguege(l1).getBody(), "French deleted!");
//         assertEquals(langCollectors.deleteLanguege(l1).getStatusCode(), HttpStatus.valueOf(400));
//         assertEquals(langCollectors.deleteLanguege(l4).getStatusCode(), HttpStatus.valueOf(400));
             

//     }
//     @Test
//     public void updateLanguege(){
//         log.info("updateAll");
//         List<String> l=new LinkedList<>();
//         l.add("Hebrew");
//         l.add("Oriya");
        
        
        
//         assertEquals(langCollectors.updateLanguege(new LanguageForChange(l3, l4)).getStatusCode(),  HttpStatus.valueOf(200)); 
//         assertEquals(langCollectors.getLangueges("sustujbv173@gmail.com").getBody(), l);
//         assertEquals(langCollectors.updateLanguege(new LanguageForChange(l1, l7)).getStatusCode(),  HttpStatus.valueOf(400)); 

//     }

}

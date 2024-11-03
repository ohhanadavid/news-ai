package com.data_manager.data_manager.BL.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.data_manager.data_manager.DAL.languege.LangaugeUser;
import com.data_manager.data_manager.DAL.languege.LangaugesForSend;
import com.data_manager.data_manager.DAL.languege.Language;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LagaugeService implements ILanguageService {

    @Autowired
    private IChecking checking;
    @Autowired
    private DaprClient daprClient;
    @Value("${NewsAiAccessor}")
    private String newsAiAccessorUrl;
    @Value("${UserAccessorUrl}")
    private String userAccessorUrl;
    
    private final String  operation="create";
    String url; 

    public ResponseEntity<?> saveLanguege(LangaugeUser language){
        log.info("Save languege");
        try{
            ResponseEntity<?> check=checking.checkUser(language.getEmail());
            if(check.getStatusCode()!= HttpStatus.OK ||!(boolean)check.getBody() )
                return  check;
            
            url=String.format("api.namberOfMyLanguages/%s",language.getEmail());
            Integer countOfMyLanguageRespose =daprClient.invokeMethod(userAccessorUrl,url,null, HttpExtension.GET,Integer.class).block();
            if(countOfMyLanguageRespose==null){
                return new ResponseEntity<>("we have problem!",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            
            Integer MaximumLanguageRespose =daprClient.invokeMethod(newsAiAccessorUrl,"api.maximumLanguage",null,HttpExtension.GET,Integer.class).block();
            if(MaximumLanguageRespose==null){
                return new ResponseEntity<>("we have problem!",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(countOfMyLanguageRespose>MaximumLanguageRespose)
                return new ResponseEntity<>(String.format("the maximum languages is :%s",MaximumLanguageRespose),HttpStatus.BAD_REQUEST);
            
            url=String.format("api.getLanguageCode/%s",language.getLanguage());    
            Map<String,String> codeRespose =daprClient.invokeMethod( newsAiAccessorUrl,url,null , HttpExtension.GET,  Map.class).block();          
            if(codeRespose==null){
                return new ResponseEntity<>("we have problem!",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            Language lToSend = new Language(language, codeRespose.get("code"));    
            daprClient.invokeBinding("api.saveLanguege", operation,lToSend).block();
            
            return new ResponseEntity<>("language saved!",HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public ResponseEntity<?> getLangueges(String email){    
        log.info("get langueges");
        try{
        url=String.format("api.getLangueges/%s", email);    
        List<String> response;
            response = daprClient.invokeMethod(userAccessorUrl, url,null,HttpExtension.GET,List.class).block();
        if (response == null)
            return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<?> getLanguegesCode(String email){  
        log.info("get langueges code");
        try{
            url=String.format("api.getLanguegesCode/%s", email);    
            List<String> response=daprClient.invokeMethod(userAccessorUrl,url ,null,HttpExtension.GET,List.class).block();
            if (response == null)
                return new ResponseEntity<>("we have problem",HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response,HttpStatus.OK);
            }catch(Exception e){
                log.error(e.getMessage());
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    
    public ResponseEntity<?> deleteLanguege(Language languege){
        log.info("delete language");
        try{
        daprClient.invokeBinding("api.deleteLanguege", operation,languege).block();
        
        return new ResponseEntity<>(String.format("%s deleted", languege),HttpStatus.OK);
        
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateLanguege(LangaugeUser oldLanguege,LangaugeUser newLanguage){
        log.info("updateAll");
        if(!oldLanguege.getEmail().equals(oldLanguege.getEmail())){
            var e= new IllegalArgumentException("email must be equals!");
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
        try {
            url=String.format("api.getLanguageCode/%s",newLanguage.getLanguage());    
            Map<String,String> codeRespose =daprClient.invokeMethod( newsAiAccessorUrl,url,null , HttpExtension.GET,  Map.class).block(); 
            if(codeRespose==null){
                return new ResponseEntity<>("we have problem!",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Language l=new Language(newLanguage, codeRespose.get("code"));
            LangaugesForSend data = new LangaugesForSend(new Language(oldLanguege), l);
            
            daprClient.invokeBinding("api.updateLanguege", operation,data).block();
            
            return new ResponseEntity<>(String.format("%s update", oldLanguege),HttpStatus.OK);
            

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

 
}

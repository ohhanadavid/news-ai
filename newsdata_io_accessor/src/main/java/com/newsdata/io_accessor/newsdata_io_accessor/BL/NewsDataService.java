package com.newsdata.io_accessor.newsdata_io_accessor.BL;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.DataLists;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.categories.CategoriesList;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.languages.Langueges;

import io.dapr.client.DaprClient;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class NewsDataService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${news.data.api}")
    private String newsDataUrl;
    @Value("${news.data.key}")
    private String newsDatakey;
    @Autowired
    private CategoriesList categoriesList;
    @Autowired
    private Langueges langueges;
    @Autowired
    DaprClient daprClient;
    @Autowired
    ObjectMapper objectMapper;

    public void getLatestNews(Map<String,Object> data){
        try{
            log.info("getLatestNews");
        UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
        queryParam("apikey", newsDatakey).
        build();

        String results = restTemplate.getForObject(url.toUriString(), String.class);
        if (results == null)
            daprClient.invokeBinding("getNews", "create","error").block();
        log.info("getLatestNews-seccess");
        data.put("artical",Base64.getEncoder().encodeToString(results.getBytes()));
        daprClient.invokeBinding("getNews", "create",data).block();
        
        }catch(RestClientException e)
        {
            log.error(e.getMessage());
            throw e;
        }

    }

    public void getLatestNewsFromTopic(String category,Map<String,Object> data){
        try{
            log.info("getLatestNewsFromTopic");
        UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
        queryParam("apikey", newsDatakey).
        queryParam("category", category).
        build();

        String results = restTemplate.getForObject(url.toUriString(), String.class);
        if (results == null)
            daprClient.invokeBinding("getNews", "create","error").block();
        log.info("getLatestNews-seccess");
        data.put("artical",Base64.getEncoder().encodeToString(results.getBytes()));
        daprClient.invokeBinding("getNews", "create",data).block();
        
        }catch(RestClientException e)
        {
            throw e;
        }

    }

    public List<String> getLatestListNewsFromCategories(Map<String,Object> data){
        List<String> resultses=new LinkedList<>();
        try{
            DataLists dataLists=objectMapper.convertValue(data.get("dataForNews"),DataLists.class);
            List<String> listOfCategories= dataLists.getCategories();
            List<String> listOfLanguages= dataLists.getLanguage();

            log.info("getLatestListNewsFromCategories");
            String langugesString=String.join(",",listOfLanguages);
            listOfCategories.parallelStream().forEach(
                category->{   
                UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
                queryParam("apikey", newsDatakey).
                queryParam("category", category).
                queryParam("language", langugesString).
                build();
            
                String results = restTemplate.getForObject(url.toUriString(), String.class);
            if(results != null){
                resultses.add(results);
            }});  
            
            data.put("artical",resultses);    
        daprClient.invokeBinding("getListNews", "create",data).block();
            return resultses;
        }catch(RestClientException e)
        {
            log.error(e.getMessage());
            throw e;
        }
    }

  

    public List<String> getCategories() {
        log.info("getCategories");
        try{
            return categoriesList.getCategories();
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
        
    }

    public Map<String, String> getLangueges() throws Exception {
        log.info("getCategories");
        try{
            return langueges.languageMap();
        }
        catch(IOException e){
            log.error(e.getMessage());
            throw  e ;
            
        }
        
        
    }
}

package com.newsdata.io_accessor.newsdata_io_accessor.BL;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.languages.LanguageWithCode;
import com.newsdata.io_accessor.newsdata_io_accessor.kafka.Producer;
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
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.languages.Languages;

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
    private Languages languages;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Producer producer;


    public void getLatestNews(Map<String,Object> data){
        try{
            log.info("getLatestNews");
        UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
        queryParam("apikey", newsDatakey).
        build();

        String results = restTemplate.getForObject(url.toUriString(), String.class);
        if (results == null)
            //daprClient.invokeBinding("getNews", "create","error").block();
            producer.send("error",Producer.GET_NEWS_TOPIC);
        log.info("getLatestNews-seccess");
        data.put("artical",Base64.getEncoder().encodeToString(results.getBytes()));
        //daprClient.invokeBinding("getNews", "create",data).block();
        producer.send(data,Producer.GET_NEWS_TOPIC);
        }catch(RestClientException e)
        {
            log.error(e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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
            //daprClient.invokeBinding("getNews", "create","error").block();
            producer.send("error",Producer.GET_NEWS_TOPIC);
        log.info("getLatestNews-seccess");
        data.put("artical",Base64.getEncoder().encodeToString(results.getBytes()));
        //daprClient.invokeBinding("getNews", "create",data).block();
        producer.send(data,Producer.GET_NEWS_TOPIC);
        
        }catch(RestClientException e)
        {
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public void getLatestListNewsFromCategories(Map<String,Object> data){
        List<String> results=new LinkedList<>();
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
            
                String result = restTemplate.getForObject(url.toUriString(), String.class);
                    results.add(result);
                });
            
            data.put("article",results);

        producer.send(data,Producer.GET_LIST_NEWS_TOPIC);
        }catch(RestClientException e)
        {
            log.error(e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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

    public List<LanguageWithCode> getLanguagesAsList() throws Exception {
        log.info("getLanguages");
        try{
            return languages.languageMap().entrySet().stream()
                    .map(entry -> new LanguageWithCode(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }
        catch(IOException e){
            log.error(e.getMessage());
            throw  e ;
            
        }
    }
    public Map<String,String> getLanguagesAsMap() throws Exception {
        log.info("getLanguagesAsMap");
        try{
            return languages.languageMap();
        }
        catch(IOException e){
            log.error(e.getMessage());
            throw  e ;

        }
    }
}

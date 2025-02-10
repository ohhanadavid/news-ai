package com.newsdata.io_accessor.newsdata_io_accessor.BL;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.*;
import com.newsdata.io_accessor.newsdata_io_accessor.DAL.languages.LanguageWithCode;
import com.newsdata.io_accessor.newsdata_io_accessor.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
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


    public void getLatestNews(DataForNews data) throws JsonProcessingException {
        log.info("getLatestNews");
        List<String> listOfLanguages= data.getLanguage();
        String langugesString=String.join(",",listOfLanguages);
        UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
            queryParam("apikey", newsDatakey)
                .queryParamIfPresent("language", Optional.of(langugesString).filter(s -> !s.isEmpty()))
                .build();
        String results = restTemplate.getForObject(url.toUriString(), String.class);

        if (results == null)
            producer.send("error",Producer.GET_NEWS_TOPIC);

        log.info("getLatestNews-success");
        ReturnData returnData = new ReturnData(data,results);
        //data.put("article",Base64.getEncoder().encodeToString(results.getBytes()));
        producer.send(returnData,Producer.GET_LIST_NEWS_TOPIC);
    }

    public void getLatestNewsFromTopic(String category, DataForNewsWithOneCategory data) throws JsonProcessingException {

        log.info("getLatestNewsFromTopic");
        List<String> listOfLanguages= data.getLanguage();
        String langugesString=String.join(",",listOfLanguages);
        UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
                queryParam("apikey", newsDatakey).
                queryParam("category", category)
                .queryParamIfPresent("language", Optional.of(langugesString).filter(s -> !s.isEmpty()))
                .build();

        String results = restTemplate.getForObject(url.toUriString(), String.class);

        if (results == null)
            producer.send("error",Producer.GET_NEWS_TOPIC);
        log.info("getLatestNewsFromTopic-success");
        ReturnData returnData = new ReturnData(data,results);
        //data.put("article",Base64.getEncoder().encodeToString(results.getBytes()));
        producer.send(returnData,Producer.GET_LIST_NEWS_TOPIC);
    }

    public void getLatestListNewsFromCategories(DataForNewsWithCategory data) throws JsonProcessingException {
        List<String> results=new LinkedList<>();
        List<String> listOfCategories= data.getDataForNews().getCategories();
        List<String> listOfLanguages= data.getLanguage();
        String langugesString=String.join(",",listOfLanguages);
        log.info("getLatestListNewsFromCategories");

        listOfCategories.parallelStream().forEach(
                category->{
                UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
                    queryParam("apikey", newsDatakey).
                    queryParam("category", category).
                    queryParamIfPresent("language", Optional.of(langugesString).filter(s -> !s.isEmpty())).
                    build();
                String result = restTemplate.getForObject(url.toUriString(), String.class);
                    results.add(result);
                });
        ReturnData returnData = new ReturnData(data,results);
       // data.put("article",String.join(results));

        producer.send(returnData,Producer.GET_LIST_NEWS_TOPIC);

    }

    public List<String> getCategories() {
        log.info("getCategories");
        return categoriesList.getCategories();
    }

    public List<LanguageWithCode> getLanguagesAsList() throws Exception {
        log.info("getLanguages");

        return languages.languageMap().entrySet().stream()
                .map(entry -> new LanguageWithCode(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());


    }

    public Map<String,String> getLanguagesAsMap() throws Exception {
        log.info("getLanguagesAsMap");

        return languages.languageMap();

    }
}

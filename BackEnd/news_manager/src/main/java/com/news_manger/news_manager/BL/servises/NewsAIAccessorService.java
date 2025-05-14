package com.news_manger.news_manager.BL.servises;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.news_manger.news_manager.DTO.articalsToGet.DataForNews;
import com.news_manger.news_manager.DTO.articalsToGet.DataForNewsWithCategory;
import com.news_manger.news_manager.DTO.articalsToGet.DataForNewsWithOneCategory;
import com.news_manger.news_manager.DTO.articalsToGet.ReturnData;
import com.news_manger.news_manager.DTO.category.CategoriesList;
import com.news_manger.news_manager.DTO.languege.LanguageWithCode;
import com.news_manger.news_manager.DTO.languege.LanguagesToMap;
import com.news_manger.news_manager.kafka.Producer;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;


@Log4j2
@Service
public class NewsAIAccessorService {

    @Autowired
    @Qualifier("withoutToken")
    private RestTemplate restTemplate;
    @Value("${news.data.api}")
    private String newsDataUrl;
    @Value("${news.data.key}")
    private String newsDatakey;
    @Autowired
    private CategoriesList categoriesList;
    @Autowired
    private LanguagesToMap languages;
    @Autowired
    Producer producer;


    public List<String> getLatestNews(List<String> languages) throws JsonProcessingException {
        log.info("getLatestNews");

        String langugesString=String.join(",",languages);
        UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
            queryParam("apikey", newsDatakey)
                .queryParamIfPresent("language", Optional.of(langugesString).filter(s -> !s.isEmpty()))
                .build();
        String results = restTemplate.getForObject(url.toUriString(), String.class);

        if (results == null)
            throw new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR);

        log.info("getLatestNews-success");
        return ( results.isEmpty()) ? null : new ArrayList<>(List.of(results));
        //return new ReturnData(data,results);
    }

    public ReturnData getLatestNewsFromTopic(DataForNewsWithOneCategory data) throws JsonProcessingException {

        log.info("getLatestNewsFromTopic");
        List<String> listOfLanguages= data.getLanguage();
        String langugesString=String.join(",",listOfLanguages);
        UriComponents url=UriComponentsBuilder.fromHttpUrl(newsDataUrl).
                queryParam("apikey", newsDatakey).
                queryParam("category", data.getCategory())
                .queryParamIfPresent("language", Optional.of(langugesString).filter(s -> !s.isEmpty()))
                .build();

        String results = restTemplate.getForObject(url.toUriString(), String.class);

        if (results == null)
            throw new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR);
        log.info("getLatestNewsFromTopic-success");
        //data.put("article",Base64.getEncoder().encodeToString(results.getBytes()));
       // producer.send(returnData,KafkaTopic.GET_LIST_NEWS_TOPIC);

        return new ReturnData(data,results);
    }

    public ReturnData getLatestListNewsFromCategories(DataForNewsWithCategory data) throws JsonProcessingException {
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
        // data.put("article",String.join(results));

      //  producer.send(returnData,KafkaTopic.GET_LIST_NEWS_TOPIC);

        return new ReturnData(data,results);

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

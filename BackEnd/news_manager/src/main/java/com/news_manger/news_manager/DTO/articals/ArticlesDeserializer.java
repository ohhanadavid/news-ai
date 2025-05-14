package com.news_manger.news_manager.DTO.articals;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ArticlesDeserializer extends JsonDeserializer<List<ArticleReturnFromLLM>> {
    @Override
    public List<ArticleReturnFromLLM> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        String articlesJson = p.getValueAsString();
        return mapper.readValue(articlesJson,
                mapper.getTypeFactory().constructCollectionType(List.class, ArticleReturnFromLLM.class));
    }
}

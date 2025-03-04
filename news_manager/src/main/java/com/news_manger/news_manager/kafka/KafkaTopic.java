package com.news_manger.news_manager.kafka;

public enum KafkaTopic {
    GET_LATEST_NEWS("api.getLatestNews"),
    GET_LATEST_NEWS_BY_CATEGORY("api.getLatestNewsByCategory"),
    GET_LATEST_LIST_NEWS_BY_CATEGORIES("api.getLatestListNewsByCategories"),
    SEND_EMAIL("api.sendMail"),
    GET_LIST_NEWS_TOPIC ("getListNews"),
    GET_NEWS_TOPIC  ("getNews"),
    GET_MY_ARTICLE("api.getMyArticle");


    private final String topic;

    KafkaTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return topic;
    }
}

package com.NewsAI.newsAiGateway.kafka;

public enum KafkaTopic {
    GET_LATEST_NEWS("getLatestNews"),
   GET_LATEST_NEWS_BY_CATEGORY("getLatestNewsByCategory"),
   GET_LATEST_NEWS_BY_MY_CATEGORIES("getLatestListNewsByCategories");


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

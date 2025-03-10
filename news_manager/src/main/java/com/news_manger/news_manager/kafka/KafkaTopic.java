package com.news_manger.news_manager.kafka;

public enum KafkaTopic {

    SEND_EMAIL("api.sendMail"),
    SEND_SMS("api.sms"),
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

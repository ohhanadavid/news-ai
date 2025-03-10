package com.news_manger.news_manager.DAL.user;

public enum SendOption {

    SMS("sms"),
    EMAIL("email");


    private final String option;

    SendOption(String option) {
        this.option = option;
    }

    public String getTopic() {
        return option;
    }

    @Override
    public String toString() {
        return option;
    }
}

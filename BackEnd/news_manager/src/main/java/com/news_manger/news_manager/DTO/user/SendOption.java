package com.news_manger.news_manager.DTO.user;

public enum SendOption {

    SMS("sms"),
    EMAIL("email"),
    WHATSAPP("whatsapp");


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

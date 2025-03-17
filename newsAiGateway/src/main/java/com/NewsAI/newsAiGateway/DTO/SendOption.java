package com.NewsAI.newsAiGateway.DTO;

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

    public static SendOption fromString(String value) {
        for (SendOption sendOption : values()) {
            if (sendOption.option.equalsIgnoreCase(value)) {
                return sendOption;
            }
        }
        throw new IllegalArgumentException("Invalid send option: " + value);
    }
}

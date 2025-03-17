package com.news_manger.news_manager.Exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ItemNotFoundException extends RuntimeException {
    private String message;

    public ItemNotFoundException(String message){
        super(message);
        this.message=message;
    }
}

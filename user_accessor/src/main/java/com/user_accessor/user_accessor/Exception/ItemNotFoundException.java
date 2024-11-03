package com.user_accessor.user_accessor.Exception;


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

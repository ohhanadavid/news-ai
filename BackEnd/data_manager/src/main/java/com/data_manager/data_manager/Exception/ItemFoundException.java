package com.data_manager.data_manager.Exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ItemFoundException extends RuntimeException {
    private String message;

    public ItemFoundException(String message){
        super(message);
        this.message=message;
    }
}

package com.data_manager.data_manager.Exception;

public class MoreThenAllowException extends RuntimeException{

    private String message;

    public MoreThenAllowException(String in,int number){
        String msg=String.format("the maximum %s is :%s",in,number);
        super(msg);
        this.message=msg;
    }
    public MoreThenAllowException(){

    }
}

package com.user_accessor.user_accessor.Exception;


public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException() {
        super("The provided password is incorrect.");
    }


    public WrongPasswordException(String message) {
        super(message);
    }


    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }


    public WrongPasswordException(Throwable cause) {
        super(cause);
    }
}

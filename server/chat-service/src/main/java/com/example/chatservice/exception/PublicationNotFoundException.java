package com.example.chatservice.exception;

public class PublicationNotFoundException extends RuntimeException{
    public PublicationNotFoundException(String message){
        super(message);
    }

    public PublicationNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

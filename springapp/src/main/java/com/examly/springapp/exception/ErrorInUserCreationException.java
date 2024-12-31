package com.examly.springapp.exception;

public class ErrorInUserCreationException extends Exception{
    public ErrorInUserCreationException(String message){
        super(message);
    } 
}

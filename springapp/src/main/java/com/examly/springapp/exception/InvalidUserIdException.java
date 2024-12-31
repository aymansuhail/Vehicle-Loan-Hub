package com.examly.springapp.exception;

public class InvalidUserIdException extends Exception{
    public InvalidUserIdException(String message){
        super(message);
    }
}

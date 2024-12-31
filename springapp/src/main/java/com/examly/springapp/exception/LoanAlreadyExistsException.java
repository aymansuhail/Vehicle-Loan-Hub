package com.examly.springapp.exception;

public class LoanAlreadyExistsException extends Exception{
    public LoanAlreadyExistsException(String message){
        super(message);
    }
}

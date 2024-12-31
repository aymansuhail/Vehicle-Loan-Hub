package com.examly.springapp.exception;

public class InvalidLoanIdException extends Exception{
    public InvalidLoanIdException(String message){
        super(message);
    }

}

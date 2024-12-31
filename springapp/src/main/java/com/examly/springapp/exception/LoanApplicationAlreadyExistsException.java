package com.examly.springapp.exception;

public class LoanApplicationAlreadyExistsException extends Exception{
    public LoanApplicationAlreadyExistsException(String message){
        super(message);
    }

}

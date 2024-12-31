package com.examly.springapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoanAlreadyExistsException.class)
    public ResponseEntity<String> handleLoanAlreadyExistsException(LoanAlreadyExistsException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

 
    @ExceptionHandler(InvalidLoanException.class)
    public ResponseEntity<String> handleInvalidLoanException(InvalidLoanException e) { 
        return ResponseEntity.status(500).body(e.getMessage());
    }
 
    @ExceptionHandler(LoanApplicationAlreadyExistsException.class)
    public ResponseEntity<String> handleLoanApplicationAlreadyExistsException(LoanApplicationAlreadyExistsException e){//addLoanApplication
        return ResponseEntity.status(500).body(e.getMessage());
    }
    @ExceptionHandler(InvalidLoanApplicationException.class)
        public ResponseEntity<String> handleInvalidLoanApplicationException(InvalidLoanApplicationException e){//addLoanApplication //getLoanApplicationById
            return ResponseEntity.status(500).body(e.getMessage());
        }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public ResponseEntity<String> handleInvalidUserIdException(InvalidUserIdException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

  
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e){//getAllLoanApplications
        return ResponseEntity.status(500).body(e.getMessage());
    }
    
    @ExceptionHandler(ErrorInUserCreationException.class)
    public ResponseEntity<String> handleErrorInUserCreationException(ErrorInUserCreationException e){
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(InvalidLoanIdException.class)
    public ResponseEntity<String> handleInvalidLoanIdException(InvalidLoanIdException e){
        return ResponseEntity.status(500).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<String>handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
        {
            Map<String,String> map = new HashMap<>();
            e.getBindingResult().getFieldErrors().forEach(error->{
                map.put( error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.status(400).body(map.toString());
 
        }
    

    }
    


    


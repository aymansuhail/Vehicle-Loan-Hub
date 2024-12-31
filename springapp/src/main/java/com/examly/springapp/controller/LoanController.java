package com.examly.springapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exception.InvalidLoanException;
import com.examly.springapp.exception.LoanAlreadyExistsException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.Loan;
import com.examly.springapp.service.LoanService;

@RestController
@CrossOrigin
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    LoanService loanService;

    @GetMapping("/api/loan/{loanId}")
    // Endpoint to retrieve a loan by its ID and return it in the response
    public ResponseEntity<Loan> getLoanById(@PathVariable long loanId) throws NotFoundException {
        logger.info("Fetching loan details for ID: {}", loanId);
         // Fetch the loan by ID using the loan service
        Loan loan = loanService.getLoanById(loanId);
        // Return the loan in the response
        return ResponseEntity.status(200).body(loan);
    }

    @PostMapping("/api/loan")
 // Endpoint to add a new loan and return the created loan in the response
    public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) throws LoanAlreadyExistsException, InvalidLoanException {
        logger.info("Adding a new loan: {}", loan);
         // Add the new loan using the loan service
        Loan newLoan = loanService.addLoan(loan);
        // Return the newly created loan in the response
        return ResponseEntity.status(201).body(newLoan);
    }

    @GetMapping("/api/loan")
    // Endpoint to retrieve all loans and return them in the response
    public ResponseEntity<List<Loan>> getAllLoans() throws NotFoundException {
        logger.info("Fetching all loans");
          // Fetch all loans using the loan service
        List<Loan> listOfLoans = loanService.getAllLoans();
        // Return the list of loans in the response 
        return ResponseEntity.status(200).body(listOfLoans);
    }

    @PutMapping("/api/loan/{loanId}")
    // Endpoint to update an existing loan and return the updated loan in the response
    public ResponseEntity<Loan> updateLoan(@PathVariable long loanId, @RequestBody Loan loan) throws NotFoundException {
        logger.info("Updating loan with ID: {}", loanId);
         // Update the loan using the loan service
        loan = loanService.updateLoan(loanId, loan);
        // Return the updated loan in the response
        return ResponseEntity.status(200).body(loan);
    }

    @DeleteMapping("/api/loan/{loanId}")
    // Endpoint to delete a loan by its ID and return a confirmation message
    public ResponseEntity<String> deleteLoan(@PathVariable long loanId) throws NotFoundException {
        logger.info("Deleting loan with ID: {}", loanId);
           // Delete the loan using the loan service
        Boolean deleteLoan = loanService.deleteLoan(loanId);
        // Return a confirmation message with a response
        return ResponseEntity.status(200).body("Loan with " + loanId + " deleted!");
    }
}

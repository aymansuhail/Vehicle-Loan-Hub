package com.examly.springapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exception.LoanApplicationAlreadyExistsException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.model.LoanApplicationDTO;
import com.examly.springapp.service.LoanApplicationService;

@RestController
@CrossOrigin
public class LoanApplicationController {
  @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationController.class);

    @Autowired
    private LoanApplicationService loanApplicationService;

    @GetMapping("/api/loanapplication") // Retrieves all loan applications
    public ResponseEntity<List<LoanApplication>> viewAllLoanApplication() {
        logger.info("Fetching all loan applications from controller");
        List<LoanApplication> listOfLoanApplication = loanApplicationService.getAllLoanApplications();
        return ResponseEntity.status(200).body(listOfLoanApplication);
    }

    @PostMapping("/api/loanapplication") // Add a new loan application
    public ResponseEntity<LoanApplication> addLoanApplication(@RequestBody LoanApplicationDTO loanapplicationDto) throws LoanApplicationAlreadyExistsException, NotFoundException {
        logger.info("Adding a new loan application");
        LoanApplication addApplication = loanApplicationService.addLoanApplication(loanapplicationDto);
        return ResponseEntity.status(201).body(addApplication);
    }

    @GetMapping("/api/loanapplication/{loanapplicationId}") // Retrieve a loan application by its ID
    public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable long loanapplicationId) throws NotFoundException {
        logger.info("Fetching loan application with ID: {}", loanapplicationId);
        LoanApplication searchLoanApplicationById = loanApplicationService.getLoanApplicationById(loanapplicationId);
        return ResponseEntity.status(200).body(searchLoanApplicationById);
    }

    @GetMapping("/api/loanapplication/user/{userId}") // Retrieve a loan application by user's ID
    public ResponseEntity<List<LoanApplication>> getLoanApplicationByUserId(@PathVariable int userId) throws NotFoundException {
        logger.info("Fetching loan applications for user ID: {}", userId);
        List<LoanApplication> listOfApplicationsByUserId = loanApplicationService.getLoanApplicationByUserId(userId);
        return ResponseEntity.status(200).body(listOfApplicationsByUserId);
    }

    @PutMapping("/api/loanapplication/{loanApplicationId}/status")
    public ResponseEntity<LoanApplication> updateLoanStatus(@PathVariable long loanApplicationId, @RequestBody Integer status) throws NotFoundException {
    logger.info("Updating loan status for application ID: {}", loanApplicationId);
    LoanApplication updatedLoanApplication = loanApplicationService.updateLoanStatus(loanApplicationId, status);
    return ResponseEntity.status(200).body(updatedLoanApplication);
}

    @DeleteMapping("/api/loanapplication/{loanapplicationId}") // Delete a loan application by its ID
    public ResponseEntity<String> deleteLoanApplication(@PathVariable long loanapplicationId) throws NotFoundException {
        logger.info("Deleting loan application with ID: {}", loanapplicationId);
        LoanApplication removeLoanApplication = loanApplicationService.deleteLoanApplication(loanapplicationId);
        return ResponseEntity.status(200).body("Loan Application Deleted Successfully!");
    }
  }
  
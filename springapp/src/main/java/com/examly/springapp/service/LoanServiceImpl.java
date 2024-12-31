package com.examly.springapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.InvalidLoanException;
import com.examly.springapp.exception.LoanAlreadyExistsException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.Loan;
import com.examly.springapp.repository.LoanRepo;

@Service
public class LoanServiceImpl implements LoanService {
    
    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Autowired
    LoanRepo loanRepo;

    @Override
    // Adds a new loan after validating the loan details
    public Loan addLoan(Loan loan) throws LoanAlreadyExistsException, InvalidLoanException {
        logger.info("Attempting to add a new loan with details: {}", loan);
        // Validate loan interest rate and maximum amount
        if (loan.getInterestRate() <= 0 || loan.getMaximumAmount() <= 0) {
            logger.warn("Invalid loan details provided: {}", loan);
            throw new InvalidLoanException("Invalid loan details provided.");
        }
         // Check if a loan with the same type, description, interest rate, and maximum
        // amount already exists
        Loan newLoan = loanRepo.findByLoanTypeAndDescriptionAndInterestRateAndMaximumAmount(
                loan.getLoanType(), loan.getDescription(), loan.getInterestRate(), loan.getMaximumAmount());
        if (newLoan != null) {
            logger.warn("Loan already exists with details: {}", newLoan);
            throw new LoanAlreadyExistsException("Loan already exists.");
        }
        // Save and return the new loan
        newLoan = loanRepo.save(loan);
        logger.info("Successfully added new loan: {}", newLoan);
        return newLoan;
    }

    @Override
    // Retrieves a loan by its ID
    public Loan getLoanById(long loanId) throws NotFoundException {
        logger.info("Fetching loan details for ID: {}", loanId);
   // Fetch loan by ID from the repository
        Loan loan = loanRepo.findById(loanId).orElse(null);
        // If loan is not found, throw an exception
        if (loan == null) {
            logger.warn("Loan details not found for ID: {}", loanId);
            throw new NotFoundException("Loan Details Not Found");
        }

        logger.info("Found loan details for ID: {}", loanId);
           // Return the loan
        return loan;
    }

    @Override
    // Retrieves all loans
    public List<Loan> getAllLoans() throws NotFoundException {
        logger.info("Fetching all loans");
 // Retrieve all loans from the repository
        List<Loan> listOfLoans = loanRepo.findAll();
        // If no loans are found, throw an exception
        if (listOfLoans.size()==0) {
            logger.warn("No loans exist in the repository");
            throw new NotFoundException("Loan does not exist.");
        }
  // Return the list of loans
        logger.info("Found {} loans in the repository", listOfLoans.size());
        return listOfLoans;
    }

    @Override
    // Updates an existing loan
    public Loan updateLoan(long loanId, Loan updatedLoan) throws NotFoundException {
        logger.info("Updating loan with ID: {}", loanId);
    // Fetch existing loan by ID from the repository
        Loan loanExist = loanRepo.findById(loanId).orElse(null);
          // If loan is not found, throw an exception
        if (loanExist == null) {
            logger.warn("Loan not found with ID: {}", loanId);
            throw new NotFoundException("Loan Not Found");
        }

        updatedLoan.setLoanId(loanId);
        Loan savedLoan = loanRepo.save(updatedLoan);
        logger.info("Successfully updated loan with ID: {}", loanId);
        return savedLoan;
    }

    @Override
    // Deletes a loan by its ID
    public Boolean deleteLoan(long loanId) throws NotFoundException {
        logger.info("Deleting loan with ID: {}", loanId);
   // If loan is not found, throw an exception
        Loan removeLoan = loanRepo.findById(loanId).orElse(null);
        // If loan is not found, throw an exception
        if (removeLoan == null) {
            logger.warn("Loan with the ID: {} does not exist to delete.", loanId);
            throw new NotFoundException("Loan with the ID: " + loanId + " does not exist to delete.");
        }
        // Delete the loan by ID
        loanRepo.deleteById(loanId);
        logger.info("Successfully deleted loan with ID: {}", loanId);
        return true;
    }
}

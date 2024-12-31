package com.examly.springapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.LoanApplicationAlreadyExistsException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.Loan;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.model.LoanApplicationDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.LoanApplicationRepo;
import com.examly.springapp.repository.LoanRepo;
import com.examly.springapp.repository.UserRepo;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    @Autowired
    LoanApplicationRepo loanApplicationRepo;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    LoanRepo loanRepo;

    @Override
    // Adds a new loan application
    public LoanApplication addLoanApplication(LoanApplicationDTO loanApplicationDTO) throws NotFoundException, LoanApplicationAlreadyExistsException {
        logger.info("Adding loan application for user ID: {} and loan ID: {}", loanApplicationDTO.getUserId(), loanApplicationDTO.getLoanId());

        User searchUser = userRepo.findById(loanApplicationDTO.getUserId()).orElse(null);
        if (searchUser == null) {
            logger.warn("User not found: {}", loanApplicationDTO.getUserId());
            throw new NotFoundException("User Not Found.");
        }

        Loan searchLoan = loanRepo.findById(loanApplicationDTO.getLoanId()).orElse(null);
        if (searchLoan == null) {
            logger.warn("Loan not found: {}", loanApplicationDTO.getLoanId());
            throw new NotFoundException("Loan Not Found.");
        }

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setSubmissionDate(loanApplicationDTO.getSubmissionDate());
        loanApplication.setIncome(loanApplicationDTO.getIncome());
        loanApplication.setModel(loanApplicationDTO.getModel());
        loanApplication.setPurchasePrice(loanApplicationDTO.getPurchasePrice());
        loanApplication.setLoanStatus(loanApplicationDTO.getLoanStatus());
        loanApplication.setAddress(loanApplicationDTO.getAddress());
        loanApplication.setFile(loanApplicationDTO.getFile());
        loanApplication.setUser(searchUser);
        loanApplication.setLoan(searchLoan);

        LoanApplication existingApplication = loanApplicationRepo.findByUserIdAndLoanId(loanApplicationDTO.getUserId(), loanApplicationDTO.getLoanId());
        if (existingApplication == null) {
            logger.info("Saving new loan application for user ID: {}", loanApplicationDTO.getUserId());
            return loanApplicationRepo.save(loanApplication);
        }
        
        logger.warn("Loan application already exists for user ID: {} and loan ID: {}", loanApplicationDTO.getUserId(), loanApplicationDTO.getLoanId());
        throw new LoanApplicationAlreadyExistsException("Loan application already exists for this applicant.");
    }

    @Override
    // Retrieves Loan Application By user ID
    public List<LoanApplication> getLoanApplicationByUserId(int userId) throws NotFoundException {
        logger.info("Getting loan applications for user ID: {}", userId);

        User searchUser = userRepo.findById(userId).orElse(null);
        if (searchUser == null) {
            logger.warn("User not found: {}", userId);
            throw new NotFoundException("User Not Found.");
        }

        List<LoanApplication> listOfApplicationsByUserId = loanApplicationRepo.findApplicationsByUserId(userId);
        if (listOfApplicationsByUserId.isEmpty()) {
            logger.warn("No loan applications found for user ID: {}", userId);
            throw new NotFoundException("No Application found for " + userId);
        }

        logger.info("Found loan applications for user ID: {}", userId);
        return listOfApplicationsByUserId;
    }

    @Override
    // Retrieves a loan application by its ID
    public LoanApplication getLoanApplicationById(long loanapplicationId) throws NotFoundException {
        logger.info("Getting loan application by ID: {}", loanapplicationId);

        LoanApplication searchLoanApplicationById = loanApplicationRepo.findById(loanapplicationId).orElse(null);
        if (searchLoanApplicationById == null) {
            logger.warn("Loan application not found by ID: {}", loanapplicationId);
            throw new NotFoundException("Loan Application with Id: " + loanapplicationId + " not found");
        }

        logger.info("Found loan application by ID: {}", loanapplicationId);
        return searchLoanApplicationById;
    }

    @Override
    // Retrieves all loan applications
    public List<LoanApplication> getAllLoanApplications() {
        logger.info("Getting all loan applications");

        List<LoanApplication> listOfApplication = loanApplicationRepo.findAll();
        logger.info("Found {} loan applications", listOfApplication.size());
        return listOfApplication;
    }

    @Override
    public LoanApplication updateLoanStatus(long loanApplicationId, Integer status) throws NotFoundException {
        logger.info("Updating loan status ID: {}", loanApplicationId);
    
        LoanApplication existingApplication = loanApplicationRepo.findById(loanApplicationId)
                .orElseThrow(() -> {
                    logger.warn("Loan application not found by ID: {}", loanApplicationId);
                    return new NotFoundException("Loan Application Not Found.");
                });
    
        existingApplication.setLoanStatus(status);
    
        logger.info("Saving updated loan status ID: {}", loanApplicationId);
        return loanApplicationRepo.save(existingApplication);
    }
    

    @Override
    // Deletes a loan application by its ID
    public LoanApplication deleteLoanApplication(long loanApplicationId) throws NotFoundException {
        logger.info("Deleting loan application ID: {}", loanApplicationId);

        LoanApplication toBeDeletedLoanApplication = loanApplicationRepo.findById(loanApplicationId).orElse(null);
        if (toBeDeletedLoanApplication != null) {
            loanApplicationRepo.deleteById(loanApplicationId);
            logger.info("Deleted loan application ID: {}", loanApplicationId);
            return toBeDeletedLoanApplication;
        } else {
            logger.warn("Loan application not found by ID: {}", loanApplicationId);
            throw new NotFoundException("Loan Application with Id: " + loanApplicationId + " not found");
        }
    }
}

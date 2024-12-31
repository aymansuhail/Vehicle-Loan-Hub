package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.exception.LoanApplicationAlreadyExistsException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.model.LoanApplicationDTO;

public interface LoanApplicationService {
    public LoanApplication addLoanApplication(LoanApplicationDTO loanapplicationDto) throws LoanApplicationAlreadyExistsException, NotFoundException;
    public List<LoanApplication> getLoanApplicationByUserId(int userId) throws NotFoundException;
    public LoanApplication getLoanApplicationById(long loanapplicationId) throws NotFoundException;
    public List<LoanApplication> getAllLoanApplications();
   public LoanApplication updateLoanStatus(long loanApplicationId, Integer status) throws NotFoundException;
    // public LoanApplication updateLoanApplication(long loanApplicationId,LoanApplicationDTO loanApplicationDTO) throws NotFoundException;
    public LoanApplication deleteLoanApplication(long loanApplicationId) throws NotFoundException;
}

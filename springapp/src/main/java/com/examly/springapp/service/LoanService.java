package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.exception.InvalidLoanException;
import com.examly.springapp.exception.LoanAlreadyExistsException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.Loan;

public interface LoanService {
    Loan addLoan(Loan loan) throws LoanAlreadyExistsException, InvalidLoanException;
    Loan getLoanById(long loanId) throws NotFoundException;
    List<Loan>getAllLoans() throws NotFoundException;
    Loan updateLoan(long loanId,Loan updatedLoan) throws NotFoundException;
    Boolean deleteLoan(long loanId) throws NotFoundException;
}

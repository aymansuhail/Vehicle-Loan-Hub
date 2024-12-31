package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;
    private String loanType;
    private String description;
    private int interestRate;
    private int maximumAmount;
    public Loan() {
    }
    public Loan(Long loanId, String loanType, String description, int interestRate, int maximumAmount) {
        this.loanId = loanId;
        this.loanType = loanType;
        this.description = description;
        this.interestRate = interestRate;
        this.maximumAmount = maximumAmount;
    }
    public Long getLoanId() {
        return loanId;
    }
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
    public String getLoanType() {
        return loanType;
    }
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }
    public int getMaximumAmount() {
        return maximumAmount;
    }
    public void setMaximumAmount(int maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    
}


    

    

    

    

    

    

    

    

    

    

    

    


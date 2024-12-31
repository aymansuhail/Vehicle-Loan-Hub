package com.examly.springapp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

@Entity
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanApplicationId;
    private LocalDate submissionDate;
    private double income;
    private String model;
    private double purchasePrice;
    private int loanStatus;
    private String address;
    private String file;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
    @ManyToOne
    @JoinColumn(name="loanId")
    private Loan loan;
    
    public Long getLoanApplicationId() {
        return loanApplicationId;
    }
    public void setLoanApplicationId(Long loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
    public double getIncome() {
        return income;
    }
    public void setIncome(double income) {
        this.income = income;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public double getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public int getLoanStatus() {
        return loanStatus;
    }
    public void setLoanStatus(int loanStatus) {
        this.loanStatus = loanStatus;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    public LoanApplication(Long loanApplicationId, LocalDate submissionDate, double income, String model,
            double purchasePrice, int loanStatus, String address, String file, User user, Loan loan) {
        this.loanApplicationId = loanApplicationId;
        this.submissionDate = submissionDate;
        this.income = income;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.loanStatus = loanStatus;
        this.address = address;
        this.file = file;
        this.user = user;
        this.loan = loan;
    }
    public LoanApplication() {
    }
    
}

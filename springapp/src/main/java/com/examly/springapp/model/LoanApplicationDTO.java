package com.examly.springapp.model;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class LoanApplicationDTO {
     @NotNull(message = "Submission date cannot be empty")
    private LocalDate submissionDate;
    @NotNull(message = "Income cannot be empty")
    @Positive(message="Income can not be negative")
    private Double income;
    @NotNull(message = "Model cannot be empty")
    private String model;
    @NotNull(message = "Purchase price cannot be empty")
    private Double purchasePrice;
    @NotNull(message = "Loan status cannot be empty")
    private Integer loanStatus;
    @NotNull(message = "Address cannot be empty")
    private String address;
    @NotNull(message = "File cannot be empty")
    private String file;
    @NotNull(message = "User cannot be empty")
    private Integer userId;
    @NotNull(message = "Loan cannot be empty")
    private Long loanId;
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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Long getLoanId() {
        return loanId;
    }
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
    
    

    

}

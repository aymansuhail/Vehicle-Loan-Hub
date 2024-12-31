package com.examly.springapp.model;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeedbackDTO {
    

    @NotBlank(message = "Feedback text cannot be empty")
    private String feedbackText;
    @NotNull(message = "Date cannot be empty")
    @FutureOrPresent(message = "Invalid Date") 
    private LocalDate date;
    @NotNull(message = "User cannot be empty")
    private Integer userId;
    public String getFeedbackText() {
        return feedbackText;
    }
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    
}

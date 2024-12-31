package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackDTO;

public interface FeedbackService {
    public Feedback addFeedback(FeedbackDTO feedbackDTO) throws NotFoundException;
    public List<Feedback> getFeedbackByUserId(int userId) throws NotFoundException;
    public List<Feedback> getAllFeedbacks() throws NotFoundException;
    public Feedback getFeedbackById(int id) throws NotFoundException;
    public Feedback updateFeedback(int id,FeedbackDTO feedbackDTO) throws NotFoundException;
    public Boolean deleteFeed(int id) throws NotFoundException;
    
}

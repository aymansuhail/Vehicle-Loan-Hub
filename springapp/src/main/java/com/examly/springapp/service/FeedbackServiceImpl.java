package com.examly.springapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.FeedbackRepo;
import com.examly.springapp.repository.UserRepo;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    FeedbackRepo feedbackRepo;

    @Override
    // Adds a new feedback after validating the user existence
    public Feedback addFeedback(FeedbackDTO feedbackDTO) throws NotFoundException {
        logger.info("Adding feedback for user with ID: {}", feedbackDTO.getUserId());
        
        User searchUser = userRepo.findById(feedbackDTO.getUserId()).orElse(null);
        if (searchUser == null) {
            logger.error("User not found with ID: {}", feedbackDTO.getUserId());
            throw new NotFoundException("User not found!");
        }

        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackDTO.getFeedbackText());
        feedback.setDate(feedbackDTO.getDate());
        feedback.setUser(searchUser);

        Feedback savedFeedback = feedbackRepo.save(feedback);
        logger.info("Feedback added with ID: {}", savedFeedback.getFeedbackId());
        return savedFeedback;
    }

    @Override
    // Retrieves feedback by user ID
    public List<Feedback> getFeedbackByUserId(int userId) throws NotFoundException {
        logger.info("Getting feedbacks for user with ID: {}", userId);

        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            logger.error("User not found with ID: {}", userId);
            throw new NotFoundException("User not found with ID: " + userId);
        }

        List<Feedback> feedbacks = feedbackRepo.getAllFeedbackForUser(userId);
        if (feedbacks.size()==0) {
            logger.warn("No feedbacks found for user ID: {}", userId);
            throw new NotFoundException("No feedbacks found for user ID: " + userId);
        }
        logger.info("Found {} feedbacks for user with ID: {}", feedbacks.size(), userId);
        return feedbacks;
    }

    @Override
    // Retrieves all feedbacks
    public List<Feedback> getAllFeedbacks() throws NotFoundException {
        logger.info("Getting all feedbacks");

        List<Feedback> feedbacks = feedbackRepo.findAll();
        // If no feedbacks are found, throw an exception
        if (feedbacks.isEmpty()) {
            logger.warn("No feedbacks found");
            throw new NotFoundException("No feedbacks found.");
        }
        logger.info("Found {} feedbacks", feedbacks.size());
        return feedbacks;
    }

    @Override
    // Retrieves feedback by ID
    public Feedback getFeedbackById(int id) throws NotFoundException {
        logger.info("Getting feedback with ID: {}", id);

        Feedback feedback = feedbackRepo.findById(id).orElse(null);
        // If feedback is not found, throw an exception
        if (feedback == null) {
            logger.error("Feedback not found with ID: {}", id);
            throw new NotFoundException("Feedback details Not found for ID:" + id);
        }
        logger.info("Found feedback with ID: {}", feedback.getFeedbackId());
        return feedback;
    }

    @Override
    // Updates an existing feedback
    public Feedback updateFeedback(int id, FeedbackDTO feedbackDTO) throws NotFoundException {
        logger.info("Updating feedback with ID: {}", id);

        Feedback existingFeedback = feedbackRepo.findById(id).orElse(null);
        // If feedback is not found, throw an exception
        if (existingFeedback == null) {
            logger.error("Feedback not found with ID: {}", id);
            throw new NotFoundException("Feedback Not Found.");
        }

        User searchUser = userRepo.findById(feedbackDTO.getUserId()).orElse(null);
        // If user is not found, throw an exception
        if (searchUser == null) {
            logger.error("User not found with ID: {}", feedbackDTO.getUserId());
            throw new NotFoundException("User Not Found.");
        }

        existingFeedback.setFeedbackText(feedbackDTO.getFeedbackText());
        existingFeedback.setDate(feedbackDTO.getDate());
        existingFeedback.setUser(searchUser);

        Feedback updatedFeedback = feedbackRepo.save(existingFeedback);
        logger.info("Feedback updated with ID: {}", updatedFeedback.getFeedbackId());
        return updatedFeedback;
    }

    @Override
    // Deletes a feedback by ID
    public Boolean deleteFeed(int id) throws NotFoundException {
        logger.info("Deleting feedback with ID: {}", id);

        Feedback foundFeedback = feedbackRepo.findById(id).orElse(null);
        if (foundFeedback != null) {
            feedbackRepo.deleteById(id);
            logger.info("Feedback deleted with ID: {}", id);
            return true;
        }
        logger.error("Feedback not found with ID: {}", id);
        throw new NotFoundException("FeedBack Not Found");
    }
}
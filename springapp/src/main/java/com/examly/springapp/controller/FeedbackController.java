package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackDTO;
import com.examly.springapp.service.FeedbackService;

@RestController
@CrossOrigin
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;
// Endpoint to retrieve all feedbacks and return them in the response
    @GetMapping("/api/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() throws NotFoundException {
        logger.info("Fetching all feedbacks");
        List<Feedback> listofFeedbacks = feedbackService.getAllFeedbacks();
        logger.info("Fetched {} feedbacks", listofFeedbacks.size());
        return ResponseEntity.status(200).body(listofFeedbacks);
    }
   // Endpoint to add a new feedback and return the created feedback in the response
    @PostMapping("/api/feedback")
    public ResponseEntity<Feedback> addFeedback(@Validated @RequestBody FeedbackDTO feedbackDTO) throws NotFoundException {
        logger.info("Adding feedback for user with ID: {}", feedbackDTO.getUserId());
        Feedback newfeedback = feedbackService.addFeedback(feedbackDTO);
        logger.info("Added feedback with ID: {}", newfeedback.getFeedbackId());
        return ResponseEntity.status(201).body(newfeedback);
    }
    // Endpoint to retrieve all feedbacks by a specific user and return them in the response
    @GetMapping("/api/feedback/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUserId(@PathVariable int userId) throws NotFoundException {
        logger.info("Fetching feedbacks for user with ID: {}", userId);
        List<Feedback> listofFeedbacks = feedbackService.getFeedbackByUserId(userId);
        logger.info("Fetched {} feedbacks for user with ID: {}", listofFeedbacks.size(), userId);
        return ResponseEntity.status(200).body(listofFeedbacks);
    }
 // Endpoint to update an existing feedback and return the updated feedback in the response
    @PutMapping("/api/feedback/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable int id, @Validated @RequestBody FeedbackDTO feedbackDTO) throws NotFoundException {
        logger.info("Updating feedback with ID: {}", id);
        Feedback editFeedback = feedbackService.updateFeedback(id, feedbackDTO);
        logger.info("Updated feedback with ID: {}", editFeedback.getFeedbackId());
        return ResponseEntity.status(200).body(editFeedback);
    }
  // Endpoint to retrieve a feedback by its ID and return it in the response
    @GetMapping("/api/feedback/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable int id) throws NotFoundException {
        logger.info("Fetching feedback with ID: {}", id);
        Feedback feedback = feedbackService.getFeedbackById(id);
        logger.info("Fetched feedback with ID: {}", feedback.getFeedbackId());
        return ResponseEntity.status(200).body(feedback);
    }
 // Endpoint to delete a feedback by its ID and return a confirmation message
    @DeleteMapping("/api/feedback/{id}")
    public ResponseEntity<String> deleteFeedBack(@PathVariable int id) throws NotFoundException {
        logger.info("Deleting feedback with ID: {}", id);
        Boolean deletedFeedback = feedbackService.deleteFeed(id);
        logger.info("Deleted feedback with ID: {}", id);
        return ResponseEntity.status(200).body("Deleted FeedBack!");
    }
}
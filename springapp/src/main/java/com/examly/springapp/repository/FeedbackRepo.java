package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Feedback;
@Repository
public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {
    
    @Query("select f from Feedback f where  f.user.userId=?1")
    List<Feedback> getAllFeedbackForUser(int userId);
}

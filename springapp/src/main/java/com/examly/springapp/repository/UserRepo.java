package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.User;


@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.email=?1 and u.username=?2")
    User findByEmailOrUsername(String email,String username);

    @Query("select u from User u where u.email=?1")
    User findByEmail(String email);
    
}

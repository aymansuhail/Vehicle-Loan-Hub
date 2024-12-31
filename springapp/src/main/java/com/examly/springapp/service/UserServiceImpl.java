package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.examly.springapp.model.RegisterDTO;
import com.examly.springapp.exception.ErrorInUserCreationException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.exception.UserAlreadyExistsException;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Override
    // // Method to create a new user
    // public RegisterDTO createUser(RegisterDTO user) throws ErrorInUserCreationException {
    //     logger.info("Attempting to create user with email: {}", user.getEmail());
    //     try {
    //         User newUser = new User();
    //         User findUser = userRepo.findByEmailOrUsername(user.getEmail(), user.getUsername());
    //         if (findUser != null) {
    //             logger.warn("User already exists with email: {} or username: {}", user.getEmail(), user.getUsername());
    //             throw new UserAlreadyExistsException("User already exists");
    //         }
    //         // Set user details
    //         newUser.setEmail(user.getEmail());
    //         newUser.setUsername(user.getUsername());
    //         newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    //         newUser.setMobileNumber(user.getMobileNumber());
    //         newUser.setUserRole(user.getUserRole());
    //         // Save the new user to the repository
    //         userRepo.save(newUser);
    //         logger.info("User created successfully with email: {}", user.getEmail());
    //          // Return the registered user details
    //         return user;
    //     } catch (Exception e) {
    //         // Log the exception
    //         logger.error("Error occurred while creating user: {}", e.getMessage());
    //         // Handle the exception by throwing a custom error
    //         throw new ErrorInUserCreationException("Sorry! User registration failed");
    //     }
    // }
    @Override
public RegisterDTO createUser(RegisterDTO user) throws ErrorInUserCreationException {
    logger.info("Attempting to create user with email: {}", user.getEmail());
    try {
        User newUser = new User();
        User findUser = userRepo.findByEmailOrUsername(user.getEmail(), user.getUsername());
        if (findUser != null) {
            logger.warn("User already exists with email: {} or username: {}", user.getEmail(), user.getUsername());
            throw new UserAlreadyExistsException("User already exists");
        }
        // Set user details
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(encryptedPassword);
        newUser.setMobileNumber(user.getMobileNumber());
        newUser.setUserRole(user.getUserRole());
        // Save the new user to the repository
        userRepo.save(newUser);
        logger.info("User created successfully with email: {}", user.getEmail());

        // Set the encrypted password in the DTO before returning
        user.setPassword(encryptedPassword);

        // Return the registered user details
        return user;
    } catch (Exception e) {
        // Log the exception
        logger.error("Error occurred while creating user: {}", e.getMessage());
        // Handle the exception by throwing a custom error
        throw new ErrorInUserCreationException("Sorry! User registration failed");
    }
}


    @Override
    // Method to authenticate a user during login
    public LoginDTO loginUser(LoginDTO user) throws NotFoundException {
        logger.info("Attempting to login user with email: {}", user.getEmail());
        User searchUser = userRepo.findByEmail(user.getEmail());
        if (searchUser == null) {
            logger.error("User not found with email: {}", user.getEmail());
            throw new NotFoundException("User doesn't exist");
        }
        if (passwordEncoder.matches(user.getPassword(), searchUser.getPassword())) {
            logger.info("User login successful for email: {}", user.getEmail());
            return user;
        }
        logger.error("User login failed for email: {}", user.getEmail());
        throw new NotFoundException("User not found");
    }

    @Override
    public User getUser(String email) throws NotFoundException {
        return userRepo.findByEmail(email);
    }
    }
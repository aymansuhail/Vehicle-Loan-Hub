package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.exception.ErrorInUserCreationException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.ApiResponse;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.RegisterDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

     // Endpoint to create a new user with validation and return the created user in the response
    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> createUser(@Validated @RequestBody RegisterDTO user) throws ErrorInUserCreationException {
        logger.info("Attempting to register user with email: {}", user.getEmail());
        RegisterDTO userCreated = userService.createUser(user);
        logger.info("User registered successfully with email: {}", userCreated.getEmail());
        return ResponseEntity.status(201).body(userCreated);
    }

    // Endpoint to authenticate a user and return a JWT token in the response
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Validated @RequestBody LoginDTO loginDTO) throws NotFoundException {
        logger.info("Attempting to authenticate user with email: {}", loginDTO.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateToken(authentication);
        User user = userService.getUser(loginDTO.getEmail());
        ApiResponse response = new ApiResponse();
        response.setStatusCode(200);
        response.setToken(jwtToken);
        response.setEmail(loginDTO.getEmail());
        response.setUserRole(user.getUserRole());
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        logger.info("User authenticated successfully with email: {}", loginDTO.getEmail());
        return ResponseEntity.status(200).body(response);
    }
}

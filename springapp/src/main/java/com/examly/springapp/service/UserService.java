package com.examly.springapp.service;

import com.examly.springapp.exception.ErrorInUserCreationException;
import com.examly.springapp.exception.NotFoundException;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.RegisterDTO;
import com.examly.springapp.model.User;

public interface UserService {
    RegisterDTO createUser(RegisterDTO user) throws ErrorInUserCreationException;
    LoginDTO loginUser(LoginDTO user) throws NotFoundException;
    public User getUser(String email) throws NotFoundException;
    
}
 
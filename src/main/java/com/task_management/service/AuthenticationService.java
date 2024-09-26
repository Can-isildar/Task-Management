package com.task_management.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    String generateToken(UserDetails userDetails);
}

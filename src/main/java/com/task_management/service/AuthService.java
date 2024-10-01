package com.task_management.service;

import com.task_management.dto.LoginRequestDTO;
import com.task_management.dto.UserRequestDTO;
import com.task_management.dto.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register (UserRequestDTO userRequestDTO);

    UserResponseDTO login(LoginRequestDTO loginDTO);
}

package com.task_management.service;


import com.task_management.dto.LoginRequestDTO;
import com.task_management.dto.UserRequestDTO;
import com.task_management.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO userDTO);

    UserResponseDTO loginUser(LoginRequestDTO loginDTO);

    void deleteUser(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO userDTO);

    UserResponseDTO patchUser(Long id, UserRequestDTO userDTO);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();


}

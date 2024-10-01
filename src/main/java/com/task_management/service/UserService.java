package com.task_management.service;


import com.task_management.dto.UserRequestDTO;
import com.task_management.dto.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    void deleteUser(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO userDTO);

    UserResponseDTO patchUser(Long id, UserRequestDTO userDTO);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    UserDetails loadUserByUsername(String username);

}

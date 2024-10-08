package com.task_management.service.impl;

import com.task_management.dto.UserRequestDTO;
import com.task_management.dto.UserResponseDTO;
import com.task_management.mapper.UserMapper;
import com.task_management.model.UserEntity;
import com.task_management.repository.UserRepository;
import com.task_management.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return userMapper.toResponseDTO(optionalUser.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity founderUser = optionalUser.get();
            founderUser.setUsername(userDTO.getUsername());
            founderUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            founderUser.setEmail(userDTO.getEmail());
            if (founderUser.getName() != null) {
                founderUser.setName(userDTO.getName());
            }
            if (founderUser.getPhone() != null) {
                founderUser.setPhone(userDTO.getPhone());
            }
            return userMapper.toResponseDTO(userRepository.save(founderUser));
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public UserResponseDTO patchUser(Long id, UserRequestDTO userDTO) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity founderUser = optionalUser.get();
            if (founderUser.getName() != null) {
                founderUser.setName(userDTO.getName());
            }
            if (founderUser.getPhone() != null) {
                founderUser.setPhone(userDTO.getPhone());
            }
            if (founderUser.getEmail() != null) {
                founderUser.setEmail(userDTO.getEmail());
            }
            if (founderUser.getPassword() != null) {
                founderUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            if (founderUser.getUsername() != null) {
                founderUser.setUsername(userDTO.getUsername());
            }
            return userMapper.toResponseDTO(userRepository.save(founderUser));
        }
        throw new RuntimeException("User not found");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member is not valid"));
    }
}

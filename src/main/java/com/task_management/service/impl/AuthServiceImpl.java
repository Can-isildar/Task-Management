package com.task_management.service.impl;

import com.task_management.dto.LoginRequestDTO;
import com.task_management.dto.UserRequestDTO;
import com.task_management.dto.UserResponseDTO;
import com.task_management.model.UserEntity;
import com.task_management.service.AuthService;
import com.task_management.mapper.UserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.task_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO userDTO) {
        UserEntity userEntity = userMapper.toEntity(userDTO);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO login(LoginRequestDTO loginDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(loginDTO.getUsername());
        if (optionalUser.isPresent()) {
            UserEntity founderUser = optionalUser.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), founderUser.getPassword())) {
                return userMapper.toResponseDTO(founderUser);
            } else {
                throw new BadCredentialsException("Wrong password");
            }
        } else {
            throw new RuntimeException("Incorrect username or password");
        }
    }


}

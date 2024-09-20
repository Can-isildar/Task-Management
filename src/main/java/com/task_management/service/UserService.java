package com.task_management.service;

import com.task_management.model.UserEntity;
import com.task_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository; // 'final' olmalı

    // Constructor ile bağımlılığı geçiyoruz
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);

    }
    public UserEntity updateUser(Long id, UserEntity user) {
        return userRepository.save(user);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

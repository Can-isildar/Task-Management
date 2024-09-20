package com.task_management.service;

import com.task_management.model.UserEntity;
import com.task_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).map(userUpdate -> {
                // Mevcut kullanıcıyı (userUpdate) güncellemek için yeni değerleri set ediyoruz
                userUpdate.setName(user.getName());
                userUpdate.setPassword(user.getPassword());
                userUpdate.setEmail(user.getEmail());
                userUpdate.setPhone(user.getPhone());
                userUpdate.setUsername(user.getUsername());
                // Güncellenen userUpdate nesnesini kaydediyoruz
                return userRepository.save(userUpdate);
            }).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        } else {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }
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

    public UserEntity patchUser(Long id, UserEntity user) {
        return userRepository.findById(id).map(userPatch -> {
            if (user.getName() != null) {
                userPatch.setName(user.getName());
            }
            if (user.getPassword() != null) {
                userPatch.setPassword(user.getPassword());
            }
            if (user.getEmail() != null) {
                userPatch.setEmail(user.getEmail());
            }
            if (user.getPhone() != null) {
                userPatch.setPhone(user.getPhone());
            }
            if (user.getUsername() != null) {
                userPatch.setUsername(user.getUsername());
            }
            return userRepository.save(userPatch);
        }).orElseThrow(() -> new RuntimeException("kullanıcı bulunamadı"));
    }
}

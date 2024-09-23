package com.task_management.service;

import com.task_management.model.UserEntity;
import com.task_management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository; // 'final' olmalı
    private final PasswordEncoder passwordEncoder;

    // Constructor ile bağımlılığı geçiyoruz
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity registerUser(UserEntity user) {
        if (user.getPassword() != null && user.getUsername() != null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
        throw new RuntimeException("Invalid username or password");
    }

    public UserEntity loginUser(UserEntity user) {
        if (user.getPassword() != null && user.getUsername() != null) {
            return null;
        }else {
            return null;
        }
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
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("İstenilen deger silinemedi");
        }
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

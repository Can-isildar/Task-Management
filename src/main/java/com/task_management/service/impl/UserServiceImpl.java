package com.task_management.service.impl;

import com.task_management.model.UserEntity;
import com.task_management.repository.UserRepository;
import com.task_management.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity registerUser(UserEntity user) {
        if (user.getPassword() != null && user.getUsername() != null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
        throw new RuntimeException("Invalid username or password");
    }

    @Override
    public UserEntity loginUser(UserEntity user) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            UserEntity foundUser = optionalUser.get();
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                return foundUser;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Invalid username");
        }
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity user) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).map(userUpdate -> {
                userUpdate.setName(user.getName());
                userUpdate.setPassword(user.getPassword());
                userUpdate.setEmail(user.getEmail());
                userUpdate.setPhone(user.getPhone());
                userUpdate.setUsername(user.getUsername());
                return userRepository.save(userUpdate);
            }).orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Unable to delete user");
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
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
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

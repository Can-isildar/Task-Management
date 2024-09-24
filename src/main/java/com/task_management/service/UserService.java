package com.task_management.service;

import com.task_management.model.UserEntity;
import java.util.List;

public interface UserService {

    UserEntity registerUser(UserEntity user);

    UserEntity loginUser(UserEntity user);

    UserEntity updateUser(Long id, UserEntity user);

    void deleteUser(Long id);

    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long id);

    UserEntity patchUser(Long id, UserEntity user);


}

package com.task_management.repository;
import com.task_management.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

}

package com.task_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;

}

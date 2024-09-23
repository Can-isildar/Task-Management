package com.task_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor  // Boş constructor ekliyoruz
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false ,length = 200 ,unique = true)
    private String username;
    @Column(nullable = false ,length = 200)
    private String password;
    @Column(nullable = false ,length = 200, unique = true)
    private String email;
    private String phone;
}

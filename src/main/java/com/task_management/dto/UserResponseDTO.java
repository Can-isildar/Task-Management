package com.task_management.dto;

import com.task_management.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String username;
    private String name;
    private String email;
    private String phone;
    private Role role;

}

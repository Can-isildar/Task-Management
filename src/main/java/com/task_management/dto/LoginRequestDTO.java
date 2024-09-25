package com.task_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Must be enter username")
    private String username;

    @NotBlank(message = "Must be enter password")
    @Size(min = 3, message = "Password must be at least 3 charters")
    private String password;
}

package com.task_management.dto;


import com.task_management.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    @NotBlank(message = "Must be enter username")
    private String username;

    @NotBlank(message = "Must be enter password")
    @Size(min = 3, message = "Password must be at least 3 charters")
    private String password; // requestte yer alacak fakat response da password yer almayacaktır.

    @Email(message = "Email should be valid")
    @NotBlank(message = "Must be enter email")
    private String email;

    private String name;

    private String phone;

    private Role role;

}

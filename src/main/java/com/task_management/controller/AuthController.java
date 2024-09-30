package com.task_management.controller;


import com.task_management.dto.LoginRequestDTO;
import com.task_management.dto.UserRequestDTO;
import com.task_management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.task_management.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userDTO) {
        UserResponseDTO registerUser = authService.register(userDTO);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginDTO) {
        UserResponseDTO loginUser = authService.login(loginDTO);
        return ResponseEntity.ok(loginUser);
    }

}

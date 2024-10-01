package com.task_management.controller;

import com.task_management.dto.LoginRequestDTO;
import com.task_management.dto.UserRequestDTO;
import com.task_management.jwt.JwtTokenUtil;
import com.task_management.service.AuthService;
import com.task_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.task_management.dto.UserResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userDTO) {
        UserResponseDTO registerUser = authService.register(userDTO);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Yanlış kullanıcı adı veya şifre", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginDTO.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        UserResponseDTO loginUser = authService.login(loginDTO);
        loginUser.setJwt(jwt);
        return ResponseEntity.ok(loginUser);
    }

}

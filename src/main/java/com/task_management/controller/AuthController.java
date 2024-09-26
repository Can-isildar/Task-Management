package com.task_management.controller;

import com.task_management.dto.AuthenticationRequest;
import com.task_management.dto.AuthenticationResponse;
import com.task_management.model.UserEntity;
import com.task_management.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Hatalı kullanıcı adı veya şifre", e);
        }

        // Kullanıcı doğrulandıysa, token oluşturup geri dön
        final String jwt = authenticationService.generateToken(new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), new ArrayList<>()));

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

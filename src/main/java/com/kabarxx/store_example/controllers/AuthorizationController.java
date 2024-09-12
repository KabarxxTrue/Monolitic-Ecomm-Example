package com.kabarxx.store_example.controllers;

import com.kabarxx.store_example.domain.dto.user.UserLoginDTO;
import com.kabarxx.store_example.domain.dto.user.UserRegistrationDTO;
import com.kabarxx.store_example.exceptions.LoginException;
import com.kabarxx.store_example.exceptions.UserAlreadyExistsException;
import com.kabarxx.store_example.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            authorizationService.register(userRegistrationDTO);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            authorizationService.login(userLoginDTO);
            return ResponseEntity.ok().build();
        } catch (LoginException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

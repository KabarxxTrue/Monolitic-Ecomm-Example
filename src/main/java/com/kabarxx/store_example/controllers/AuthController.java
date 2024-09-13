package com.kabarxx.store_example.controllers;

import com.kabarxx.store_example.security.authentication.SignInRequest;
import com.kabarxx.store_example.security.authentication.SignUpRequest;
import com.kabarxx.store_example.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}

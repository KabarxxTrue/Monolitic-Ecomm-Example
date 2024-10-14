package com.kabarxx.store_example.ui;

import com.kabarxx.store_example.application.authentication.SignInRequest;
import com.kabarxx.store_example.application.authentication.SignUpRequest;
import com.kabarxx.store_example.application.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}

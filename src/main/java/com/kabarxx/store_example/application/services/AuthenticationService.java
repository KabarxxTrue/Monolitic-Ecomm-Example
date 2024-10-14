package com.kabarxx.store_example.application.services;

import com.kabarxx.store_example.application.authentication.SignInRequest;
import com.kabarxx.store_example.application.authentication.SignUpRequest;
import com.kabarxx.store_example.application.exceptions.authentication.AuthenticationException;
import com.kabarxx.store_example.domain.services.UserDomainService;
import com.kabarxx.store_example.infrastructure.repositories.UserRepository;
import com.kabarxx.store_example.application.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    @Autowired
    public AuthenticationService(JwtService jwtService, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager, UserRepository userRepository,
                                 UserDomainService userDomainService) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
    }

    public String signUp(SignUpRequest request) {

        userDomainService.checkIfUserExists(request.getUsername(), request.getEmail(), userRepository);

        var user = userDomainService.createUser(request.getUsername(), request.getEmail(), request.getPassword(), passwordEncoder);

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String signIn(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        var user = userDomainService.loadUserByUsername(request.getUsername());

        return jwtService.generateToken(user);
    }
}

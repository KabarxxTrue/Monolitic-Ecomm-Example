package com.kabarxx.store_example.services;

import com.kabarxx.store_example.security.authentication.SignInRequest;
import com.kabarxx.store_example.security.authentication.SignUpRequest;
import com.kabarxx.store_example.domain.enumerations.UserRoles;
import com.kabarxx.store_example.exceptions.authentication.AuthenticationException;
import com.kabarxx.store_example.exceptions.authentication.UserAlreadyExistsException;
import com.kabarxx.store_example.repositories.UserRepository;
import com.kabarxx.store_example.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.kabarxx.store_example.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserService userService, JwtService jwtService,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                 UserRepository userRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    public String signUp(SignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username is already taken.");

        if (userRepository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistsException("Email is already taken");

        var user = new User() {
            {
                setUsername(request.getUsername());
                setEmail(request.getEmail());
                setPassword(passwordEncoder.encode(request.getPassword()));
                setRole(UserRoles.USER);
            }
        };

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String signIn(SignInRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        return jwtService.generateToken(user);
    }
}

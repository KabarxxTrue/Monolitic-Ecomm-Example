package com.kabarxx.store_example.services;

import com.kabarxx.store_example.domain.dto.authentication.SignInRequest;
import com.kabarxx.store_example.domain.dto.authentication.SignUpRequest;
import com.kabarxx.store_example.domain.enumerations.UserRolesEnum;
import com.kabarxx.store_example.exceptions.AuthenticationException;
import com.kabarxx.store_example.exceptions.UserAlreadyExistsException;
import com.kabarxx.store_example.repositories.UserRepository;
import com.kabarxx.store_example.security.jwt.JwtAuthenticationResponse;
import com.kabarxx.store_example.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.kabarxx.store_example.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username is already taken.");

        if (userRepository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistsException("Email is already taken");

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRolesEnum.USER)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password.");
        }

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}

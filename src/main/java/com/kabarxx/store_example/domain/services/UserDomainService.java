package com.kabarxx.store_example.domain.services;

import com.kabarxx.store_example.application.exceptions.authentication.UserAlreadyExistsException;
import com.kabarxx.store_example.domain.UserRoles;
import com.kabarxx.store_example.domain.models.User;
import com.kabarxx.store_example.infrastructure.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDomainService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email, String rawPassword, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(UserRoles.USER);

        return user;
    }

    public UserDetailsService userDetailsService() {
        return this;
    }

    public void checkIfUserExists(String username, String email, UserRepository userRepository) {
        if (userRepository.existsByUsername(username))
            throw new RuntimeException("Username is already taken.");

        if (userRepository.existsByEmail(email))
            throw new UserAlreadyExistsException("Email is already taken.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}

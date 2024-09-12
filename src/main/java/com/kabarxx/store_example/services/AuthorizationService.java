package com.kabarxx.store_example.services;

import com.kabarxx.store_example.domain.User;
import com.kabarxx.store_example.domain.dto.user.UserLoginDTO;
import com.kabarxx.store_example.domain.dto.user.UserRegistrationDTO;
import com.kabarxx.store_example.domain.enumerations.UserRolesEnum;
import com.kabarxx.store_example.exceptions.InvalidPasswordException;
import com.kabarxx.store_example.exceptions.UserAlreadyExistsException;
import com.kabarxx.store_example.exceptions.UserNotFoundException;
import com.kabarxx.store_example.mappers.UserMapper;
import com.kabarxx.store_example.repositories.UserRepository;
import com.kabarxx.store_example.security.CustomUserDetailsService;
import com.kabarxx.store_example.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                UserMapper userMapper, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void register(UserRegistrationDTO userRegistrationDTO) {
        boolean userExists = userRepository.existsByUsernameOrEmail(
                userRegistrationDTO.getUsername(),
                userRegistrationDTO.getEmail()
        );

        if (userExists)
            throw new UserAlreadyExistsException();

        User user = userMapper.toEntity(userRegistrationDTO);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(UserRolesEnum.USER);
        userRepository.save(user);
    }

    public void login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword()))
            throw new InvalidPasswordException();

        jwtTokenUtil.generateToken(user);
    }
}

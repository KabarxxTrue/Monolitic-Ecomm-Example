package com.kabarxx.store_example.services;

import com.kabarxx.store_example.domain.User;
import com.kabarxx.store_example.domain.dto.user.UserDTO;
import com.kabarxx.store_example.domain.dto.user.UserLoginDTO;
import com.kabarxx.store_example.domain.dto.user.UserRegistrationDTO;
import com.kabarxx.store_example.domain.enumerations.UserRolesEnum;
import com.kabarxx.store_example.exceptions.InvalidPasswordException;
import com.kabarxx.store_example.exceptions.UserAlreadyExistsException;
import com.kabarxx.store_example.exceptions.UserNotFoundException;
import com.kabarxx.store_example.mappers.UserMapper;
import com.kabarxx.store_example.repositories.UserRepository;
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
                                UserMapper userMapper, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserDTO register(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByUsernameOrEmail(userRegistrationDTO.getUsername(), userRegistrationDTO.getEmail()))
            throw new UserAlreadyExistsException();

        User user = userMapper.toEntity(userRegistrationDTO);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(UserRolesEnum.USER);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public String login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword()))
            throw new InvalidPasswordException();

        return jwtTokenUtil.generateToken(user); // Todo
    }
}

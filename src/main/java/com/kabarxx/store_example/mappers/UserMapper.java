package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.domain.User;
import com.kabarxx.store_example.domain.dto.user.UserDTO;
import com.kabarxx.store_example.domain.dto.user.UserRegistrationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper
{
    User toEntity(UserRegistrationDTO userRegistrationDTO);

    UserDTO toDTO(User user);
}

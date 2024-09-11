package com.kabarxx.store_example.domain.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO
{
    private String username;
    private String password;
    private String email;
    private String imageUrl;
}

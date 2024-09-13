package com.kabarxx.store_example.security.authentication;

import lombok.*;

@Getter
@Setter
public class SignUpRequest
{
    private String username;

    private String email;

    private String password;
}

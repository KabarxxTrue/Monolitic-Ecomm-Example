package com.kabarxx.store_example.domain;

import com.kabarxx.store_example.domain.enumerations.UserRolesEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "User_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image")
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRolesEnum roles;

    public User(String username, String password, String email,
                String imageUrl, UserRolesEnum roles)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
        this.roles = roles;
    }
}

package com.atmymelo.atmymelobackend.entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String name;

    private String bio;

    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private ROLE role;

    private LocalDateTime createdAt;

}
package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(@Email String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(@NotNull(message = "Username cannot be Empty.") String username);
}

package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(@Email String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}

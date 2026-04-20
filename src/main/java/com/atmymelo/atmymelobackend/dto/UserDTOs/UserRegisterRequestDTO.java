package com.atmymelo.atmymelobackend.dto.UserDTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
        @NotNull(message = "Username cannot be Empty.")
        String username,
        @Email(message = "Enter a valid Email address.")
        String email,
        @Size(min = 8, message = "Password must be of at least 8 characters")
        String password,
        @NotNull
        @Size(min = 3, max = 18, message = "Name must be between 6 - 18 characters")
        String name
) {
}

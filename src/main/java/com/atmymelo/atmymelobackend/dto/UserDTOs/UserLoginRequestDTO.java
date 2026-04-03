package com.atmymelo.atmymelobackend.dto.UserDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDTO(
        @Email(message = "Enter a valid email address")
        String email,
        @Size(min = 6,max = 18, message = "Password must be of size 6 - 18 characters")
        String password
) {
}

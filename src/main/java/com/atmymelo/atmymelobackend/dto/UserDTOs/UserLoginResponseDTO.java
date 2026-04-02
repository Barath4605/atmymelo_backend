package com.atmymelo.atmymelobackend.dto.UserDTOs;

import java.util.UUID;

public record UserLoginResponseDTO(
        String message,
        String username,
        UUID id
) {
}

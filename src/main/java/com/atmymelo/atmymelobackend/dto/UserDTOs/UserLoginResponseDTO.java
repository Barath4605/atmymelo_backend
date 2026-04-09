package com.atmymelo.atmymelobackend.dto.UserDTOs;

import java.util.UUID;

public record UserLoginResponseDTO(
        String username,
        String name,
        UUID userId,
        String token
) {
}

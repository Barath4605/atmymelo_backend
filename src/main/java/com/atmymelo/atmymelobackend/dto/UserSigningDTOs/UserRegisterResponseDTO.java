package com.atmymelo.atmymelobackend.dto.UserSigningDTOs;

import java.util.UUID;

public record UserRegisterResponseDTO(
        String username,
        String email,
        UUID uuid
) {
}

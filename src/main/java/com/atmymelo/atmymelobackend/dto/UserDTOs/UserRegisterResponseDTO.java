package com.atmymelo.atmymelobackend.dto.UserDTOs;

import java.util.UUID;

public record UserRegisterResponseDTO(
        String username,
        String email,
        UUID uuid
) {
}

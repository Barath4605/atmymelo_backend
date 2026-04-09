package com.atmymelo.atmymelobackend.dto.UserDTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRegisterResponseDTO (
        UUID userId,
        String username,
        String name
) {}

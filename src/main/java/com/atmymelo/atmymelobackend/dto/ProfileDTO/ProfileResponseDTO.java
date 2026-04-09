package com.atmymelo.atmymelobackend.dto.ProfileDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProfileResponseDTO(
        UUID id,
        String username,
        String name,
        String bio,
        LocalDateTime joined
) {
}

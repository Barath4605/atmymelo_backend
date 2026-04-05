package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponseDTO(
        @NotBlank
        String review,
        LocalDateTime reviewDate,
        UUID reviewId
) {
}

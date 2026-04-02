package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        @NotBlank
        String review,
        LocalDateTime reviewDate
) {
}

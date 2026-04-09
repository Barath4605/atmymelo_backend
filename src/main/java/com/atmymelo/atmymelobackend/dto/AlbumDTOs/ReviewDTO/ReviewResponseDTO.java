package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO;

import com.atmymelo.atmymelobackend.entity.Album;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponseDTO(
        @NotBlank
        String review,
        Album album,
        LocalDateTime reviewDate,
        UUID reviewId
) {
}

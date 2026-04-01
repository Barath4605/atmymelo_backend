package com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO;

import jakarta.validation.constraints.Size;

public record RatingRequestDTO(
        @Size(min = 1, max = 5)
        int rating
) {
}

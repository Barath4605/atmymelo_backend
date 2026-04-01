package com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO;

import com.atmymelo.atmymelobackend.entity.Album;

public record RatingResponseDTO(
        Album album,
        int rating
) {
}

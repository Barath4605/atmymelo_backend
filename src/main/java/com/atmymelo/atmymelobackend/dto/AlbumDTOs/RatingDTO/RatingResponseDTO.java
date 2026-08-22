package com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;

public record RatingResponseDTO(
        Album album,
        Double rating
) {
}

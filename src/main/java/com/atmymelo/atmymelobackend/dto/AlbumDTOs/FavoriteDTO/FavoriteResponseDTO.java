package com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;

public record FavoriteResponseDTO(
        Album album,
        boolean favorite
) {
}

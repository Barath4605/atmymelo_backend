package com.atmymelo.atmymelobackend.dto.AlbumDTOs.QueueDTO;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;

public record QueueResponseDTO(
        Album album,
        boolean queue
) {
}

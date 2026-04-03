package com.atmymelo.atmymelobackend.dto.AlbumDTOs.QueueDTO;

import com.atmymelo.atmymelobackend.entity.Album;

public record QueueResponseDTO(
        Album album,
        boolean queue
) {
}

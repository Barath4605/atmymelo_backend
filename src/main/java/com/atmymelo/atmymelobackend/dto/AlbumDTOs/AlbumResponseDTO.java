package com.atmymelo.atmymelobackend.dto.AlbumDTOs;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;

public record AlbumResponseDTO(
        Album album,
        Double rating,
        Boolean favorite,
        Boolean queue
) {}

package com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO;

public record FavoriteGenreResponseDTO(
        String albumId,
        String title,
        String artist,
        Double rating,
        Integer releaseDate,
        String imageUrl
) {}

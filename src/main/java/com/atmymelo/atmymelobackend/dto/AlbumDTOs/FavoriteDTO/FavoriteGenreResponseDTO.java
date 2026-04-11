package com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO;

public record FavoriteGenreResponseDTO(
        String albumId,
        String title,
        String artist,
        int rating,
        Integer releaseDate,
        String imageUrl
) {}

package com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO;

public record FavoriteGenreResponseDTO(

        // ALBUM
        String albumId,
        String title,
        Integer releaseDate,
        String imageUrl,

        // ARTIST
        String artist,
        String artistId,

        // USER
        Double rating
) {}

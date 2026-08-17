package com.atmymelo.atmymelobackend.dto;

public record ArtistTopSongsDto(

        // ARTIST
        String artistId,
        String artistName,

        // ALBUM
        String albumId,
        String albumName,
        String albumImg,

        // SONG DATA
        String trackId,
        String trackName,
        Double rating,
        String duration

) {
}

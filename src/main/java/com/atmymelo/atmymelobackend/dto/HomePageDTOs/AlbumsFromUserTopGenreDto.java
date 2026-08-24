package com.atmymelo.atmymelobackend.dto.HomePageDTOs;

public record AlbumsFromUserTopGenreDto(

        // GENRE
        String genre,

        //ALBUM
        String albumId,
        String albumName,
        String albumImg,

        //ARTIST
        String artistId,
        String artistName
) {
}

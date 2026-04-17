package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ArtistAlbumsDTO;

public record AudioDbAlbumDTO(
        String idAlbum,
        String idArtist,
        String strAlbum,
        String strAlbumThumb,
        String strReleaseFormat,
        String intYearReleased,
        String strGenre,
        String strDescription,
        String strMusicBrainzID
) {}
package com.atmymelo.atmymelobackend.dto.TrackDTOs;

public record TracklistRequestDTO(

        String strTrack,
        String strAlbum,
        String strArtist,
        String intDuration,
        String strMusicBrainzAlbumID

) {}

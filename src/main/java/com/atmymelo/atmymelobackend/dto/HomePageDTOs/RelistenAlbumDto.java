package com.atmymelo.atmymelobackend.dto.HomePageDTOs;

import java.time.LocalDateTime;

public record RelistenAlbumDto(
    LocalDateTime updatedAt,

    //Album
    String albumId,
    String albumName,
    String albumImg,

    //Artist
    String artistId,
    String artistName
) {
}

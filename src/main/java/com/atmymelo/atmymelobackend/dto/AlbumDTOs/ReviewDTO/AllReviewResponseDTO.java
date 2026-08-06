package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record AllReviewResponseDTO(

        // REVIEW
        String content,
        LocalDateTime createdAt,
        UUID reviewId,
        Boolean relisten,

        // USER
        UUID userId,
        String username,

        //ALBUM
        String albumName,
        String albumId,
        String albumUrl,
        Integer rating,
        Integer likes,

        // ARTIST
        String artist,
        String artistId

) {}

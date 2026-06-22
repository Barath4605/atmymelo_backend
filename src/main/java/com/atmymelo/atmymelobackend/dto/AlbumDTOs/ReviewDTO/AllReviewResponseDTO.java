package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO;

import com.atmymelo.atmymelobackend.entity.Review;

import java.time.LocalDateTime;
import java.util.UUID;

public record AllReviewResponseDTO(

        // REVIEW
        String content,
        LocalDateTime createdAt,
        UUID reviewId,

        // USER
        UUID userId,
        String username,

        //ALBUM
        String albumName,
        String albumId,
        String albumUrl,
        Integer rating,

        // ARTIST
        String artist,
        String artistId

) {
}

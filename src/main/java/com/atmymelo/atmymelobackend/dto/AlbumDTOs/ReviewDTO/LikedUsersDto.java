package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO;

import java.util.List;
import java.util.UUID;

public record LikedUsersDto(
        Album album,
        List<LikedUser> likedUsers
) {

    public record Album(
            String id,
            String name,
            String imageUrl,
            Artist artist
    ) {}

    public record Artist(
            String id,
            String name
    ) {}

    public record LikedUser(
            UUID userId,
            String username,
            Double rating,
            Boolean isFavorite
    ) {}
}
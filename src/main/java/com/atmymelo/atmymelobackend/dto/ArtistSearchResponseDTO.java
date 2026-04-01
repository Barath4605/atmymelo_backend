package com.atmymelo.atmymelobackend.dto;

public record ArtistSearchResponseDTO(
        String id,
        String name,
        String country,
        String imageUrl
) {}
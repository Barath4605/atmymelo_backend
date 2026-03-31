package com.atmymelo.atmymelobackend.dto;

public record ArtistSearchDTO(
        String id,
        String name,
        String country,
        String imageUrl
) {}
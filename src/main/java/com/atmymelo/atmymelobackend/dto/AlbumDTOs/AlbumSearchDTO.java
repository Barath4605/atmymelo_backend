package com.atmymelo.atmymelobackend.dto.AlbumDTOs;

public record AlbumSearchDTO(
        String id,
        String title,
        String artist,
        String year
) {}
package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ArtistAlbumsDTO;

import java.util.List;

public record AudioDbResponseDTO(
        List<AudioDbAlbumDTO> album
) {}
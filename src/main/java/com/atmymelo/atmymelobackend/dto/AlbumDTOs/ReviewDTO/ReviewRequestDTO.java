package com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReviewRequestDTO(
        String review,
        LocalDateTime date
) {}

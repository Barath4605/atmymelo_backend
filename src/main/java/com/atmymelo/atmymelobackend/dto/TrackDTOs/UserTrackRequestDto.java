package com.atmymelo.atmymelobackend.dto.TrackDTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;

public record UserTrackRequestDto(

        @Min(value = 1, message = "Rating must be between 1 and 5")
        @Max(value = 5, message = "Rating must be between 1 and 5")
        Double rating,
        Boolean favorite
) {}

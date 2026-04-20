package com.atmymelo.atmymelobackend.dto.TrackDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TracklistResponseDTO(
        @JsonProperty("track")
        List<TracklistRequestDTO> tracklist
) {
}

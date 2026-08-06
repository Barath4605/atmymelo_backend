package com.atmymelo.atmymelobackend.dto.TrackDTOs.TrackListDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TracklistResponseDTO(
        @JsonProperty("track")
        List<TracklistRequestDTO> tracklist
) {
}

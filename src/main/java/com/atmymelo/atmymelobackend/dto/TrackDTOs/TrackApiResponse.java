package com.atmymelo.atmymelobackend.dto.TrackDTOs;

import java.util.List;

public record TrackApiResponse(
        List<TracklistRequestDTO> tracks
) {}

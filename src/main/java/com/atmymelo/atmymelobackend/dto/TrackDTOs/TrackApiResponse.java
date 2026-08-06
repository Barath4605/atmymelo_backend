package com.atmymelo.atmymelobackend.dto.TrackDTOs;

import com.atmymelo.atmymelobackend.dto.TrackDTOs.TrackListDto.TracklistRequestDTO;

import java.util.List;

public record TrackApiResponse(
        List<TracklistRequestDTO> tracks
) {}

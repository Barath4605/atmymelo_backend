package com.atmymelo.atmymelobackend.controller.TracklistController;

import com.atmymelo.atmymelobackend.dto.TrackDTOs.TracklistResponseDTO;
import com.atmymelo.atmymelobackend.service.TracklistService.FetchTracklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TracklistController {

    private final FetchTracklistService fetchTracklistService;

    @GetMapping("/{mbid}")
    public TracklistResponseDTO getTracklist(@PathVariable String mbid){

        return fetchTracklistService.fetchTracklist(mbid);
    }

}

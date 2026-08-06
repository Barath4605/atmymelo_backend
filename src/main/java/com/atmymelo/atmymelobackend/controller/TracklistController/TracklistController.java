package com.atmymelo.atmymelobackend.controller.TracklistController;

import com.atmymelo.atmymelobackend.dto.TrackDTOs.TrackListDto.TracklistResponseDTO;
import com.atmymelo.atmymelobackend.service.TracklistService.FetchTracklistService;
import com.atmymelo.atmymelobackend.service.TracklistService.TracklistService;
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
    private final TracklistService tracklistService;

    @GetMapping("/{mbid}")
    public TracklistResponseDTO getTracklist(@PathVariable String mbid){

        return tracklistService.saveTracklist(mbid);
    }


}

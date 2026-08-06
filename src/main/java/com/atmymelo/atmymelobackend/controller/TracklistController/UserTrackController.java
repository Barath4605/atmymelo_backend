package com.atmymelo.atmymelobackend.controller.TracklistController;

import com.atmymelo.atmymelobackend.dto.TrackDTOs.UserTrackRequestDto;
import com.atmymelo.atmymelobackend.dto.TrackDTOs.UserTrackResponseDto;
import com.atmymelo.atmymelobackend.service.TracklistService.TracklistService;
import com.atmymelo.atmymelobackend.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tracks")
public class UserTrackController {

    private final TracklistService tracklistService;
    private final JwtUtil jwtUtil;

    @PostMapping("/rate-track/{tadbId}")
    public UserTrackRequestDto userRating(@PathVariable String tadbId,
                                          @RequestBody UserTrackRequestDto userTrackRequestDto,
                                          @RequestHeader("Authorization") String authHeader){

        UUID userId = jwtUtil.extractUserId(authHeader);

        return tracklistService.userRating(userTrackRequestDto, tadbId,  userId);
    }

    @GetMapping("/get-track-rating/{tadbId}")
    public UserTrackResponseDto getTrackRating(@PathVariable String tadbId,
                                               @RequestHeader("Authorization") String authHeader){

        UUID userId = jwtUtil.extractUserId(authHeader);

        return tracklistService.getUserRating(tadbId, userId);
    }


}

package com.atmymelo.atmymelobackend.controller.AlbumController;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteGenreResponseDTO;
import com.atmymelo.atmymelobackend.service.AlbumService.QueueService;
import com.atmymelo.atmymelobackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;
    private final JwtUtil jwtUtil;

    @GetMapping("/genres")
    public List<String> getQueueGenres(@RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtUtil.extractUserId(authHeader);
        return queueService.getUserQueueGenres(userId);
    }

    @GetMapping("/albums")
    public List<FavoriteGenreResponseDTO> getQueueAlbums(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String genre) {

        UUID userId = jwtUtil.extractUserId(authHeader);
        return queueService.getQueueAlbumsByGenre(userId, genre);
    }
}
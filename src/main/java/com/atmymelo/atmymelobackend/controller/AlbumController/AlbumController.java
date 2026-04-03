package com.atmymelo.atmymelobackend.controller.AlbumController;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.AlbumResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.AlbumSearchDTO;
import com.atmymelo.atmymelobackend.service.AlbumService.AlbumService;
import com.atmymelo.atmymelobackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;
    private final JwtUtil jwtUtil;

    @GetMapping("/albums/search")
    public List<AlbumSearchDTO> search(@RequestParam String query) {
        return albumService.searchAlbums(query);
    }

    @GetMapping("/albums/{mbid}")
    public AlbumResponseDTO getAlbum(@PathVariable String mbid,
                                     @RequestHeader("Authorization") String authHeader) {

        UUID userId = jwtUtil.extractUserId(authHeader);

        return albumService.getAlbumByMbid(mbid, userId);
    }
}
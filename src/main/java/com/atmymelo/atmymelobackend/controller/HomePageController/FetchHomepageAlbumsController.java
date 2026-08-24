package com.atmymelo.atmymelobackend.controller.HomePageController;

import com.atmymelo.atmymelobackend.dto.HomePageDTOs.AlbumsFromUserTopGenreDto;
import com.atmymelo.atmymelobackend.dto.HomePageDTOs.RelistenAlbumDto;
import com.atmymelo.atmymelobackend.service.HomePageService.HomepageAlbumService;
import com.atmymelo.atmymelobackend.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/homepage")
@AllArgsConstructor
public class FetchHomepageAlbumsController {

    private final JwtUtil jwtUtil;
    private final HomepageAlbumService homepageAlbumService;

    // SUGGEST RE LISTEN ALBUMS
    @GetMapping("/suggest-relisten")
    public ResponseEntity<List<RelistenAlbumDto>> suggestRelisten(@RequestHeader("Authorization") String authHeader){
        UUID userId =  jwtUtil.extractUserId(authHeader);

        return ResponseEntity.ok(homepageAlbumService.suggestRelisten(userId));

    }


    // SUGGEST ALBUMS BASED ON USER TOP GENRE
    @GetMapping("/suggest-albums-topgenre")
    public ResponseEntity<List<AlbumsFromUserTopGenreDto>> getAlbumsFromUserTopGenre(@RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtUtil.extractUserId(authHeader);

        return ResponseEntity.ok(homepageAlbumService.getAlbumsFromUserTopGenre(userId));

    }

}

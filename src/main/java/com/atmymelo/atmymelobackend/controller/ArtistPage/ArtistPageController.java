package com.atmymelo.atmymelobackend.controller.ArtistPage;

import com.atmymelo.atmymelobackend.dto.ArtistTopSongsDto;
import com.atmymelo.atmymelobackend.service.ArtistService.ArtistService;
import com.atmymelo.atmymelobackend.service.ArtistService.ArtistTopSongs;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/artist/")
@RestController
@AllArgsConstructor
public class ArtistPageController {

    private final ArtistTopSongs  artistTopSongs;

    @GetMapping("top-songs/{artistId}")
    public ResponseEntity<List<ArtistTopSongsDto>> getArtistTopSongs(@PathVariable String artistId) {

        return ResponseEntity.ok(artistTopSongs.getArtistTopSongs(artistId));

    }

}

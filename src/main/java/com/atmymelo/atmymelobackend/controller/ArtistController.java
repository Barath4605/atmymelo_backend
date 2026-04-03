package com.atmymelo.atmymelobackend.controller;

import com.atmymelo.atmymelobackend.dto.ArtistSearchResponseDTO;
import com.atmymelo.atmymelobackend.entity.Artist;
import com.atmymelo.atmymelobackend.service.ArtistService.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }


    // ARTIST SEARCH
    @GetMapping("/search")
    public List<ArtistSearchResponseDTO> search(@RequestParam String query) {
        return artistService.searchArtists(query);
    }

    // ARTIST PAGE
    @GetMapping("/artist/{mbid}")
    public Artist getArtist(@PathVariable String mbid) {
        return artistService.getArtistByMbid(mbid);
    }
}

package com.atmymelo.atmymelobackend.controller.AlbumController;

import com.atmymelo.atmymelobackend.entity.Album;
import com.atmymelo.atmymelobackend.service.AlbumService.ArtistAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistAlbumController {

    private final ArtistAlbumService artistAlbumService;

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<Album>> getArtistAlbums(@PathVariable String id) {
        return ResponseEntity.ok(artistAlbumService.getArtistAlbums(id));
    }
}
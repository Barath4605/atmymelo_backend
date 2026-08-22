package com.atmymelo.atmymelobackend.controller.HomePageController;

import com.atmymelo.atmymelobackend.dto.HomePageDTOs.RelistenAlbumDto;
import com.atmymelo.atmymelobackend.service.HomePageController.HomepageAlbumService;
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

    private final HomepageAlbumService homepageAlbumService;
    private final JwtUtil jwtUtil;

    @GetMapping("/suggest-relisten")
    public ResponseEntity<List<RelistenAlbumDto>> suggestRelisten(@RequestHeader("Authorization") String authHeader){
        UUID userId =  jwtUtil.extractUserId(authHeader);

        return ResponseEntity.ok(homepageAlbumService.suggestRelisten(userId));

    }

}

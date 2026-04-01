package com.atmymelo.atmymelobackend.controller.AlbumController;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO.RatingRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO.RatingResponseDTO;
import com.atmymelo.atmymelobackend.service.AlbumService.AlbumRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumRatingController {

    private final AlbumRatingService albumRatingService;

    @PostMapping("/{mbid}/rate")
    public ResponseEntity<RatingResponseDTO> rating(@RequestBody RatingRequestDTO dto,
                                                    @RequestParam UUID userId,
                                                    @PathVariable String mbid) {


        return new ResponseEntity<>(albumRatingService.rate(dto, userId, mbid), HttpStatus.OK);
    }

    @PostMapping("/{mbid}/favorite")
    public ResponseEntity<FavoriteResponseDTO> favorite(@RequestBody FavoriteRequestDTO dto,
                                                        @RequestParam UUID userId,
                                                        @PathVariable String mbid) {

        return new ResponseEntity<>(albumRatingService.favorite(dto, userId, mbid), HttpStatus.OK);
    }

}

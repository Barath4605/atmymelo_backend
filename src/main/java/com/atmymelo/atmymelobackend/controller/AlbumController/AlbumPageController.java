package com.atmymelo.atmymelobackend.controller.AlbumController;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.QueueDTO.QueueRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.QueueDTO.QueueResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO.RatingRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO.RatingResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.AllReviewResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewResponseDTO;
import com.atmymelo.atmymelobackend.service.AlbumService.AlbumRatingService;
import com.atmymelo.atmymelobackend.service.AlbumService.ReviewService;
import com.atmymelo.atmymelobackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumPageController {

    private final AlbumRatingService albumRatingService;
    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    // RATING
    @PostMapping("/{mbid}/rate")
    public ResponseEntity<RatingResponseDTO> rating(@RequestBody RatingRequestDTO dto,
                                                    @RequestHeader("Authorization") String authHeader,
                                                    @PathVariable String mbid) {


        UUID userId = jwtUtil.extractUserId(authHeader);

        return new ResponseEntity<>(albumRatingService.rate(dto, userId, mbid), HttpStatus.OK);
    }

    // FAVORITES
    @PostMapping("/{mbid}/favorite")
    public ResponseEntity<FavoriteResponseDTO> favorite(@RequestBody FavoriteRequestDTO dto,
                                                        @RequestHeader("Authorization") String authHeader,
                                                        @PathVariable String mbid) {

        UUID userId = jwtUtil.extractUserId(authHeader);

        return new ResponseEntity<>(albumRatingService.favorite(dto, userId, mbid), HttpStatus.OK);
    }

    // QUEUE
    @PostMapping("/{mbid}/queue")
    public ResponseEntity<QueueResponseDTO> queue(@RequestBody QueueRequestDTO dto,
                                                  @RequestHeader("Authorization") String authHeader,
                                                  @PathVariable String mbid) {

        UUID userId = jwtUtil.extractUserId(authHeader);

        return new ResponseEntity<>(albumRatingService.queue(dto, userId, mbid), HttpStatus.OK);
    }

    //REVIEW
    @PostMapping("/{mbid}/review")
    public ResponseEntity<ReviewResponseDTO> review(@RequestBody ReviewRequestDTO dto,
                                                    @RequestHeader("Authorization") String authHeader,
                                                    @PathVariable String mbid) {

        UUID userId = jwtUtil.extractUserId(authHeader);

        return new ResponseEntity<>(reviewService.review(dto, userId, mbid), HttpStatus.OK);
    }

    // FETCH ALL REVIEWS
    @GetMapping("/{mbid}/all-reviews")
    public ResponseEntity<List<AllReviewResponseDTO>> allReviews(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable String mbid) {

        UUID userId = jwtUtil.extractUserId(authHeader);
        List<AllReviewResponseDTO> reviews = reviewService.allReviews(userId, mbid);

        return ResponseEntity.ok(reviews);
    }

}

package com.atmymelo.atmymelobackend.controller.AlbumController;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.AllReviewResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewResponseDTO;
import com.atmymelo.atmymelobackend.service.AlbumService.ReviewService;
import com.atmymelo.atmymelobackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class ReviewController {

    private final JwtUtil jwtUtil;
    private final ReviewService reviewService;

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

    // FETCH LAST 3 REVIEWS
    @GetMapping("/{mbid}/last-3")
    public ResponseEntity<List<AllReviewResponseDTO>> last5Reviews(@RequestHeader("Authorization") String authHeader,
                                                                   @PathVariable String mbid) {
        UUID userId = jwtUtil.extractUserId(authHeader);
        List<AllReviewResponseDTO> reviews = reviewService.fetchLast3Reviews(userId, mbid);

        return ResponseEntity.ok(reviews);
    }


    // DELETE REVIEW
    @DeleteMapping("/delete-review/{reviewId}")
    public void deleteReview(@RequestHeader("Authorization") String authHeader,
                             @PathVariable UUID reviewId) {

        UUID userId = jwtUtil.extractUserId(authHeader);
        reviewService.deleteReview(reviewId,userId);
    }


}

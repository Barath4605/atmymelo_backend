package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.AllReviewResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewResponseDTO;
import com.atmymelo.atmymelobackend.entity.Review;
import com.atmymelo.atmymelobackend.entity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.ReviewRepository;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserAlbumRepository userAlbumRepository;
    private final ReviewRepository reviewRepository;

    public ReviewResponseDTO review(ReviewRequestDTO reviewDto, UUID userId, String mbid) {

        Review review = new Review();
        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId,mbid);

        review.setAlbum(userAlbum.getAlbum());
        review.setUser(userAlbum.getUser());

        review.setContent(reviewDto.review());
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);

        return new ReviewResponseDTO(review.getContent(), review.getCreatedAt());

    }

    public List<AllReviewResponseDTO> allReviews(UUID userId, String mbid) {

        List<Review> reviews = reviewRepository.findAllByUserIdAndAlbumId(userId, mbid);

        return reviews.stream()
                .map(review -> new AllReviewResponseDTO(
                        // REVIEW
                        review.getContent(),
                        review.getCreatedAt(),
                        review.getId(),

                        // USER
                        review.getUser().getId(),
                        review.getUser().getUsername(),

                        // ALBUM
                        review.getAlbum().getTitle(),
                        review.getAlbum().getId(),
                        review.getAlbum().getImageUrl(),

                        // ARTIST
                        review.getAlbum().getArtist().getName(),
                        review.getAlbum().getArtist().getId()
                ))
                .toList();

    }

}

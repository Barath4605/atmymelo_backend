package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.AllReviewResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewResponseDTO;
import com.atmymelo.atmymelobackend.entity.Album;
import com.atmymelo.atmymelobackend.entity.Review;
import com.atmymelo.atmymelobackend.entity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
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
    private final AlbumRepository albumRepository;
    private final ReviewRepository reviewRepository;

    // USER POST REVIEW
    public ReviewResponseDTO review(ReviewRequestDTO reviewDto, UUID userId, String mbid) {

        Review review = new Review();
        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId,mbid);
        Album album = albumRepository.getById(mbid);

        review.setAlbum(userAlbum.getAlbum());
        review.setUser(userAlbum.getUser());

        review.setContent(reviewDto.review());
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);

        return new ReviewResponseDTO(review.getContent(),album ,review.getCreatedAt(), review.getId());

    }

    // FETCH ALL REVIEWS
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

    // DELETE REVIEW
    public void deleteReview(UUID reviewId, UUID id) {
        reviewRepository.deleteById(reviewId);
    }

    public List<AllReviewResponseDTO> fetchLast3Reviews(UUID userId, String mbid) {

        List<Review> reviews = reviewRepository.findTop3ByUserIdAndAlbumIdOrderByCreatedAtDesc(userId, mbid);

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

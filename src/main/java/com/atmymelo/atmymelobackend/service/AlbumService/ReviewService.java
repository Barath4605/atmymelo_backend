package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.AllReviewResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.ReviewResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.TotalLikeAndIsLikedDTO;
import com.atmymelo.atmymelobackend.entity.*;
import com.atmymelo.atmymelobackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final UserAlbumRepository userAlbumRepository;
    private final AlbumRepository albumRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final UserRepository userRepository;

    // USER POST REVIEW
    public ReviewResponseDTO review(ReviewRequestDTO reviewDto, UUID userId, String mbid) {

        Review review = new Review();
        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId,mbid);
        Album album = albumRepository.getById(mbid);

        review.setAlbum(userAlbum.getAlbum());
        review.setUser(userAlbum.getUser());

        review.setContent(reviewDto.review());
        review.setCreatedAt(LocalDateTime.now());

        review.setRating(userAlbum.getRating());

        review.setLikes(0);

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
                        review.getRating(),
                        review.getLikes(),

                        // ARTIST
                        review.getAlbum().getArtist().getName(),
                        review.getAlbum().getArtist().getId()
                ))
                .toList();

    }

    //LIKE REVIEW
    public boolean toggleLike(UUID reviewId, UUID userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        if(reviewLikeRepository.existsByReviewIdAndUserId(reviewId, userId)) {
            reviewLikeRepository.deleteByReviewIdAndUserId(reviewId, userId);
            review.setLikes(review.getLikes() - 1);
            return false;
        }

        User user = userRepository.findById(userId).orElseThrow();

        ReviewLike reviewLike = new ReviewLike();
        reviewLike.setReview(review);
        reviewLike.setUser(user);

        review.setLikes(review.getLikes() +1);

        reviewLikeRepository.save(reviewLike);

        System.out.println("toggleLike called");

        return true;
    }

    // GET TOTAL LIKES FOR A REVIEW
    public TotalLikeAndIsLikedDTO getTotalLike(UUID reviewId, UUID userId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow();

        int totalLikes = review.getLikes();
        boolean isLiked = reviewLikeRepository.existsByReviewIdAndUserId(reviewId, userId);

        return new TotalLikeAndIsLikedDTO(
                totalLikes,
                isLiked
        );

    }

    // DELETE REVIEW
    public void deleteReview(UUID reviewId, UUID id) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        reviewLikeRepository.deleteByReview(review);
        reviewRepository.deleteById(reviewId);
    }

    // GET LAST 3 REVIEWS OF THE USER
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
                        review.getRating(),
                        review.getLikes(),

                        // ARTIST
                        review.getAlbum().getArtist().getName(),
                        review.getAlbum().getArtist().getId()
                ))
                .toList();
    }
}

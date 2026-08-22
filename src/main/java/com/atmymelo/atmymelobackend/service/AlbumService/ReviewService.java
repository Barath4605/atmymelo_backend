package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ReviewDTO.*;
import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;
import com.atmymelo.atmymelobackend.entity.AlbumEntity.UserAlbum;
import com.atmymelo.atmymelobackend.entity.ReviewEntity.Review;
import com.atmymelo.atmymelobackend.entity.ReviewEntity.ReviewLike;
import com.atmymelo.atmymelobackend.entity.UserEntity.User;
import com.atmymelo.atmymelobackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        boolean relisten = reviewRepository.existsByUserAndAlbum(userAlbum.getUser(), album);
        review.setRelisten(relisten);

        review.setAlbum(userAlbum.getAlbum());
        review.setUser(userAlbum.getUser());

        review.setContent(reviewDto.review());
        review.setCreatedAt(reviewDto.date());

        review.setRating(userAlbum.getRating());

        review.setLikes(0);

        reviewRepository.save(review);

        return new ReviewResponseDTO(review.getContent(),album ,review.getCreatedAt(), review.getId(), relisten);

    }

    // FETCH ALL REVIEWS
    public List<AllReviewResponseDTO> allUserReviews(UUID userId, String mbid) {

        List<Review> reviews = reviewRepository.findAllByUserIdAndAlbumId(userId, mbid);

        return reviews.stream()
                .map(review -> new AllReviewResponseDTO(
                        // REVIEW
                        review.getContent(),
                        review.getCreatedAt(),
                        review.getId(),
                        review.getRelisten(),

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

    // GET ALL REVIEWS / SORT IT BY POPULARITY
    public List<AllReviewResponseDTO>  allReviews(String mbid) {
        List<Review> allReviews = reviewRepository.findAllByAlbumIdOrderByLikesDesc(mbid);

        return allReviews.stream()
                .map(review -> new AllReviewResponseDTO(
                        // REVIEW
                        review.getContent(),
                        review.getCreatedAt(),
                        review.getId(),
                        review.getRelisten(),

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

    // GET ALL THE USERS WHO LIKED A REVIEW
    public LikedUsersDto getLikedUsers(UUID reviewId) {

        List<ReviewLike> likes =
                reviewLikeRepository.findAllByReviewId(reviewId);

        // No likes
        if (likes.isEmpty()) {
            return new LikedUsersDto(
                    null,
                    List.of()
            );
        }

        // Get review and album
        Review review = likes.get(0).getReview();
        Album album = review.getAlbum();

        // ALBUM + ARTIST
        LikedUsersDto.Artist artist =
                new LikedUsersDto.Artist(
                        album.getArtist().getId(),
                        album.getArtist().getName()
                );

        LikedUsersDto.Album albumDto =
                new LikedUsersDto.Album(
                        album.getId(),
                        album.getTitle(),
                        album.getImageUrl(),
                        artist
                );

        // LIKED USERS
        List<LikedUsersDto.LikedUser> likedUsers =
                likes.stream()
                        .map(like -> {

                            User user = like.getUser();

                            UserAlbum userAlbum =
                                    userAlbumRepository.findByUserAndAlbum(
                                            user,
                                            album
                                    );

                            // User can like a review even if they don't
                            // have a UserAlbum entry for this album.
                            Double rating = null;
                            Boolean isFavorite = null;

                            if (userAlbum != null) {
                                rating = userAlbum.getRating();
                                isFavorite = userAlbum.getIsFavorite();
                            }

                            return new LikedUsersDto.LikedUser(
                                    user.getId(),
                                    user.getUsername(),
                                    rating,
                                    isFavorite
                            );
                        })
                        .toList();

        return new LikedUsersDto(
                albumDto,
                likedUsers
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
                        review.getRelisten(),

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

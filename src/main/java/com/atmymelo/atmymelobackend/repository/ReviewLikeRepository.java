package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.UserAlbum;
import com.atmymelo.atmymelobackend.entity.ReviewEntity.Review;
import com.atmymelo.atmymelobackend.entity.ReviewEntity.ReviewLike;
import com.atmymelo.atmymelobackend.entity.UserEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, UUID> {
    boolean existsByReviewIdAndUserId(UUID reviewId, UUID userId);
    void deleteByReviewIdAndUserId(UUID reviewId, UUID userId);
    void deleteByReview(Review review);

    List<ReviewLike> findAllByReviewId(UUID reviewId);
}

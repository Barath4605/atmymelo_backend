package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.Review;
import com.atmymelo.atmymelobackend.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, UUID> {
    boolean existsByReviewIdAndUserId(UUID reviewId, UUID userId);
    void deleteByReviewIdAndUserId(UUID reviewId, UUID userId);
    void deleteByReview(Review review);
}

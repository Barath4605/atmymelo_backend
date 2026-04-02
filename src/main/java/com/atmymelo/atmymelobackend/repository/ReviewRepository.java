package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUserIdAndAlbumId(UUID userId, String mbid);
}

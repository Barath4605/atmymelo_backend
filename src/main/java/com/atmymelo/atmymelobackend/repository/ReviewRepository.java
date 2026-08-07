package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;
import com.atmymelo.atmymelobackend.entity.ReviewEntity.Review;
import com.atmymelo.atmymelobackend.entity.UserEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUserIdAndAlbumId(UUID userId, String mbid);

    List<Review> findTop3ByUserIdAndAlbumIdOrderByCreatedAtDesc(UUID userId, String albumId);

    boolean existsByUserAndAlbum(User user, Album album);

    List<Review> findAllByAlbumIdOrderByLikesDesc(String albumId);
}

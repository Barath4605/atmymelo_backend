package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.UserAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserAlbumRepository extends JpaRepository<UserAlbum, Long> {

    UserAlbum findByUserIdAndAlbumId(UUID userId, String mbid);

    @Query("""
        SELECT a.genre
        FROM UserAlbum ua
        JOIN ua.album a
        WHERE ua.user.id = :userId
        AND ua.isFavorite = true
        GROUP BY a.genre
    """)
    List<String> findUserFavoriteGenres(UUID userId);

    @Query("""
        SELECT ua
        FROM UserAlbum ua
        JOIN ua.album a
        WHERE ua.user.id = :userId
        AND ua.isFavorite = true
        AND a.genre = :genre
    """)
    List<UserAlbum> findFavoritesByGenre(UUID userId, String genre);

    @Query("""
        SELECT a.genre
        FROM UserAlbum ua
        JOIN ua.album a
        WHERE ua.user.id = :userId
        AND ua.inQueue = true
        GROUP BY a.genre
    """)
    List<String> findUserQueueGenres(UUID userId);

    @Query("""
        SELECT ua
        FROM UserAlbum ua
        JOIN ua.album a
        WHERE ua.user.id = :userId
        AND ua.inQueue = true
        AND LOWER(a.genre) = LOWER(:genre)
    """)
    List<UserAlbum> findQueueByGenre(UUID userId, String genre);

}

package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;
import com.atmymelo.atmymelobackend.entity.TracklistEntity.Tracklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface TracklistRepository extends JpaRepository<Tracklist, UUID> {

    boolean existsByTrackNameAndAlbum(String s, Album album);

    Tracklist findTracklistByTadbTrackId(String tadbTrackId);

    @Query("""
            SELECT t.tadbTrackId
            FROM Tracklist t
            WHERE t.album.id = :albumId
            AND t.ratingCount > 0
            ORDER BY (t.ratingSum * 1.0 / t.ratingCount) DESC
            """)
    List<String> findTopRatedTrackId(
            @Param("albumId") String albumId,
            Pageable pageable
    );
}

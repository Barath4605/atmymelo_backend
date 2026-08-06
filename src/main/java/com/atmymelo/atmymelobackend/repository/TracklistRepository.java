package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;
import com.atmymelo.atmymelobackend.entity.TracklistEntity.Tracklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TracklistRepository extends JpaRepository<Tracklist, UUID> {

    boolean existsByTrackNameAndAlbum(String s, Album album);

    Tracklist findTracklistByTadbTrackId(String tadbTrackId);
}

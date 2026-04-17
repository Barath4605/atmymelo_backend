package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, String> {
    Optional<Artist> findByTadbArtistId(String tadbArtistId);
}

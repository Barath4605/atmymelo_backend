package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, String> {
}

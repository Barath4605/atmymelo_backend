package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, String> {
    List<Album> findByArtistId(String mbid);

}

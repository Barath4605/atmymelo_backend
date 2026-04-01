package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, String> {
}

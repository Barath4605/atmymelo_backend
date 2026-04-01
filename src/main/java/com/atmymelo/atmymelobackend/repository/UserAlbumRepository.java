package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.UserAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAlbumRepository extends JpaRepository<UserAlbum, Long> {
    UserAlbum findByUserIdAndAlbumId(UUID userId, String mbid);
}

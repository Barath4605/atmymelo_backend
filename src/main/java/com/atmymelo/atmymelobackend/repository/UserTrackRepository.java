package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.TracklistEntity.UserTrack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserTrackRepository extends JpaRepository<UserTrack, UUID> {
    UserTrack getUserTrackByUser_IdAndTracklist_TadbTrackId(UUID userId, String tracklistTadbTrackId);
}

package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.Tracklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TracklistRepository extends JpaRepository<Tracklist, UUID> {

}

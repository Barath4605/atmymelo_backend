package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.ReportEntity.ArtistReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistReportRepository extends JpaRepository<ArtistReport, Long> {
    Optional<ArtistReport> findByArtistId(String artistId);
}

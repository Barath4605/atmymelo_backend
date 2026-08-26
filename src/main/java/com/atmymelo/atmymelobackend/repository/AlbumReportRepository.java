package com.atmymelo.atmymelobackend.repository;

import com.atmymelo.atmymelobackend.entity.ReportEntity.AlbumReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumReportRepository extends JpaRepository<AlbumReport, Long> {
    Optional<AlbumReport> findByAlbumId(String albumId);
}

package com.atmymelo.atmymelobackend.service.ReportService;

import com.atmymelo.atmymelobackend.dto.ReportDTOs.PostReportDto;
import com.atmymelo.atmymelobackend.entity.ReportEntity.AlbumReport;
import com.atmymelo.atmymelobackend.entity.ReportEntity.ArtistReport;
import com.atmymelo.atmymelobackend.repository.AlbumReportRepository;
import com.atmymelo.atmymelobackend.repository.ArtistReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportService {

    private final ArtistReportRepository artistReportRepository;
    private final AlbumReportRepository albumReportRepository;

    // SUBMIT A REPORT FOR ALBUM
    public void submitAlbumReport(PostReportDto postReport) {

        Optional<AlbumReport> existing = albumReportRepository.findByAlbumId(postReport.id());

        if (existing.isPresent()) {
            AlbumReport albumReport = existing.get();
            albumReport.setTickets(albumReport.getTickets() + 1);

            albumReportRepository.save(albumReport);
            return;
        }

        AlbumReport newReport = new AlbumReport();

        newReport.setComplaint(postReport.complaint());
        newReport.setAlbumId(postReport.id());
        newReport.setTickets(1L);

        albumReportRepository.save(newReport);
    }

    // SUBMIT A REPORT FOR ALBUM
    public void submitArtistReport(PostReportDto postReport) {

        Optional<ArtistReport> existing = artistReportRepository.findByArtistId(postReport.id());

        if (existing.isPresent()) {
             ArtistReport artistReport = existing.get();
            artistReport.setTickets(artistReport.getTickets() + 1);

            artistReportRepository.save(artistReport);
            return;
        }

        ArtistReport newReport = new ArtistReport();

        newReport.setComplaint(postReport.complaint());
        newReport.setArtistId(postReport.id());
        newReport.setTickets(1L);

        artistReportRepository.save(newReport);
    }

}

package com.atmymelo.atmymelobackend.controller.ReportController;

import com.atmymelo.atmymelobackend.dto.ReportDTOs.PostReportDto;
import com.atmymelo.atmymelobackend.service.ReportService.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // SUBMIT ALBUM REPORT
    @PostMapping("/album")
    public ResponseEntity<Void> albumReport(@RequestBody PostReportDto postReport) {
        reportService.submitAlbumReport(postReport);
        return ResponseEntity.ok().build();
    }

    // SUBMIT ARTIST REPORT
    @PostMapping("/artist")
    public ResponseEntity<Void> artistReport(@RequestBody PostReportDto postReport) {
        reportService.submitArtistReport(postReport);
        return ResponseEntity.ok().build();
    }

}

package com.atmymelo.atmymelobackend.dto.ReportDTOs;

import com.atmymelo.atmymelobackend.entity.ReportEntity.ComplaintType;

public record PostReportDto(
    String id,
    ComplaintType complaint
) {
}

package com.atmymelo.atmymelobackend.entity.ReportEntity;

import com.atmymelo.atmymelobackend.entity.ArtistEntity.Artist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ArtistReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ComplaintType complaint;

    private Long tickets;

    @NotNull
    private String artistId;
}

package com.atmymelo.atmymelobackend.entity.TracklistEntity;

import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;
import com.atmymelo.atmymelobackend.entity.ArtistEntity.Artist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Tracklist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    private String trackName;

    private String artistName;

    private String duration;

    private String trackNumber;

    private Integer ratingCount;

    private Integer ratingSum;

    @Column(unique = true)
    private String tadbTrackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Artist artist;

}

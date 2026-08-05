package com.atmymelo.atmymelobackend.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Album album;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Artist artist;

}

package com.atmymelo.atmymelobackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "artist_images")
public class ArtistImage {

    @Id
    private String mbid;

    private String thumbUrl;
    private String backgroundUrl;
    private String logoUrl;

    private LocalDateTime lastUpdated;
}
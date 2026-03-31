package com.atmymelo.atmymelobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "albums")
@Getter
@Setter
public class Album {

    @Id
    private String id; // external API id

    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private Integer releaseYear;

    private String genre;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

}
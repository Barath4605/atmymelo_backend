package com.atmymelo.atmymelobackend.entity.AlbumEntity;

import com.atmymelo.atmymelobackend.entity.ArtistEntity.Artist;
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

    private String tadbAlbumId;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private Integer releaseYear;

    private String genre;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

}
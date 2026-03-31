package com.atmymelo.atmymelobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "artists")
public class Artist {

    @Id
    private String id; // external API id

    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String backdropUrl;

    private String logoUrl;

    private String photoUrl;

    private String label;

    private String born;

    private String formed;

}
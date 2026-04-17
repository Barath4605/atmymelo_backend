package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ArtistAlbumsDTO.AudioDbResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AudioDbClient {

    private final RestTemplate restTemplate;

    private static final String BASE_URL =
            "https://www.theaudiodb.com/api/v1/json/2/album.php?i=";

    public AudioDbClient() {
        this.restTemplate = new RestTemplate();
    }

    public AudioDbResponseDTO fetchAlbums(String tadbArtistId) {

        String url = BASE_URL + tadbArtistId;

        try {
            return restTemplate.getForObject(url, AudioDbResponseDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
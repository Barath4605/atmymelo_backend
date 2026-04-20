package com.atmymelo.atmymelobackend.service.TadbClient;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ArtistAlbumsDTO.AudioDbResponseDTO;
import com.atmymelo.atmymelobackend.dto.TrackDTOs.TrackApiResponse;
import com.atmymelo.atmymelobackend.dto.TrackDTOs.TracklistResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AudioDbClient {

    private final RestTemplate restTemplate;

    // FETCH ALBUM
    public AudioDbResponseDTO fetchAlbums(String tadbArtistId) {

        String url = "https://www.theaudiodb.com/api/v1/json/2/album.php?i=" + tadbArtistId;

        try {
            return restTemplate.getForObject(url, AudioDbResponseDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    //FETCH ALBUM TRACKLIST
    public TracklistResponseDTO fetchTracklist(String tadbAlbumId) {

        String url = "https://www.theaudiodb.com/api/v1/json/2/track.php?m=" + tadbAlbumId;

        try {
            return restTemplate.getForObject(url, TracklistResponseDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
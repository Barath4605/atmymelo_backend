package com.atmymelo.atmymelobackend.service.TracklistService;

import com.atmymelo.atmymelobackend.dto.TrackDTOs.TracklistResponseDTO;
import com.atmymelo.atmymelobackend.entity.Album;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
import com.atmymelo.atmymelobackend.service.TadbClient.AudioDbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchTracklistService {

    private final AudioDbClient audioDbClient;
    private final AlbumRepository albumRepository;

    public TracklistResponseDTO fetchTracklist(String mbid) {

        Album album = albumRepository.findById(mbid).orElseThrow(() -> new RuntimeException("Album not found"));

        String tadbId = album.getTadbAlbumId();

        return audioDbClient.fetchTracklist(tadbId);
    }


}

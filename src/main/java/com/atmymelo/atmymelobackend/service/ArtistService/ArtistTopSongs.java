package com.atmymelo.atmymelobackend.service.ArtistService;

import com.atmymelo.atmymelobackend.dto.ArtistTopSongsDto;
import com.atmymelo.atmymelobackend.entity.TracklistEntity.Tracklist;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
import com.atmymelo.atmymelobackend.repository.ArtistRepository;
import com.atmymelo.atmymelobackend.repository.TracklistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArtistTopSongs {

    private final TracklistRepository tracklistRepository;

    public List<ArtistTopSongsDto> getArtistTopSongs(String artistId) {


        List<Tracklist> getArtistTracklist = tracklistRepository.findTopRatedByArtistId(artistId);

        return getArtistTracklist.stream().map(track -> new ArtistTopSongsDto(

                track.getArtist().getId(),
                track.getArtist().getName(),

                track.getAlbum().getId(),
                track.getAlbum().getTitle(),
                track.getAlbum().getImageUrl(),

                track.getTadbTrackId(),
                track.getTrackName(),
                track.getRatingCount() > 0
                        ? (double) track.getRatingSum() / track.getRatingCount()
                        : 0.0,
                track.getDuration()

        )).toList();

    }
}

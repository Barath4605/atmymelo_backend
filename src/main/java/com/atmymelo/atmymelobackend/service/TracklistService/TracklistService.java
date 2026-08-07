package com.atmymelo.atmymelobackend.service.TracklistService;

import com.atmymelo.atmymelobackend.dto.TrackDTOs.TrackListDto.TracklistResponseDTO;
import com.atmymelo.atmymelobackend.dto.TrackDTOs.UserTrackRequestDto;
import com.atmymelo.atmymelobackend.dto.TrackDTOs.UserTrackResponseDto;
import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;
import com.atmymelo.atmymelobackend.entity.TracklistEntity.Tracklist;
import com.atmymelo.atmymelobackend.entity.TracklistEntity.UserTrack;
import com.atmymelo.atmymelobackend.entity.UserEntity.User;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
import com.atmymelo.atmymelobackend.repository.TracklistRepository;
import com.atmymelo.atmymelobackend.repository.UserRepository;
import com.atmymelo.atmymelobackend.repository.UserTrackRepository;
import com.atmymelo.atmymelobackend.service.TadbClient.AudioDbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TracklistService {

    private final TracklistRepository tracklistRepository;
    private final AlbumRepository albumRepository;
    private final AudioDbClient audioDbClient;
    private final UserRepository userRepository;
    private final UserTrackRepository userTrackRepository;

    // FETCH AND PERSIST THE TRACKLIST
    public TracklistResponseDTO saveTracklist(String mbid) {

        Album album = albumRepository.findById(mbid)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        String tadbId = album.getTadbAlbumId();

        TracklistResponseDTO response =
                audioDbClient.fetchTracklist(tadbId);

        List<Tracklist> tracks = response.tracklist()
                .stream()
                .filter(dto -> !tracklistRepository.existsByTrackNameAndAlbum(
                        dto.strTrack(),
                        album
                ))
                .map(dto -> {

                    Tracklist track = new Tracklist();

                    track.setTrackName(dto.strTrack());
                    track.setArtistName(dto.strArtist());
                    track.setDuration(dto.intDuration());
                    track.setAlbum(album);
                    track.setArtist(album.getArtist());
                    track.setTadbTrackId(dto.idTrack());
                    track.setTrackNumber(dto.intTrackNumber());
                    track.setRatingCount(0);
                    track.setRatingSum(0);

                    return track;

                })
                .toList();

        tracklistRepository.saveAll(tracks);

        return new TracklistResponseDTO(response.tracklist());
    }

    // GET USER RATING FOR A TRACK
    public UserTrackRequestDto userRating(UserTrackRequestDto userTrackRequestDto,
                                          String tadbId,
                                          UUID userId) {

        Boolean favorite = userTrackRequestDto.favorite();
        Integer rating = userTrackRequestDto.rating();

        User user = userRepository.findById(userId).orElseThrow();
        Tracklist tracklist = tracklistRepository.findTracklistByTadbTrackId(tadbId);

        UserTrack userTrack =
                userTrackRepository
                        .getUserTrackByUser_IdAndTracklist_TadbTrackId(
                                userId,
                                tadbId
                        );

        if(userTrack == null){
            userTrack = new UserTrack();
            userTrack.setUser(user);
            userTrack.setTracklist(tracklist);

            tracklist.setRatingCount(
                    tracklist.getRatingCount() + 1
            );
        } else {
            tracklist.setRatingSum(
                    tracklist.getRatingSum() - userTrack.getRating()
            );
        }

        userTrack.setCreatedAt(LocalDateTime.now());

        userTrack.setFavorite(favorite);
        userTrack.setRating(rating);

        userTrack.setUser(user);
        userTrack.setTracklist(tracklist);

        tracklist.setRatingSum(tracklist.getRatingSum() + rating);

        userTrackRepository.save(userTrack);
        tracklistRepository.save(tracklist);

        return new UserTrackRequestDto(userTrack.getRating(), userTrack.getFavorite());
    }

    // FETCH THE AVERAGE RATING AND THE USER RATING FOR TRACKS
    public UserTrackResponseDto getUserRating(String tadbId,
                                              UUID userId) {

        Tracklist tracklist = tracklistRepository.findTracklistByTadbTrackId(tadbId);

        Double average = 0.0;

        if(tracklist.getRatingCount() > 0){
            average = (double) tracklist.getRatingSum()
                    / tracklist.getRatingCount();
        }

        UserTrack usertrack = userTrackRepository.getUserTrackByUser_IdAndTracklist_TadbTrackId(userId, tracklist.getTadbTrackId());
        Integer userRating = null;

        if(usertrack != null){
            userRating = usertrack.getRating();
        }

        return new UserTrackResponseDto(userRating,average);

    }

    // GET THE TOP SONG FROM THE ALBUM TRACKLIST
    public String getTopTrack(String mbid) {

        return tracklistRepository
                .findTopRatedTrackId(mbid, PageRequest.of(0,1))
                .get(0);
    }
}
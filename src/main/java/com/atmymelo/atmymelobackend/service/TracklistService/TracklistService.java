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
                    track.setRatingSum(0.0);

                    return track;

                })
                .toList();

        tracklistRepository.saveAll(tracks);

        return new TracklistResponseDTO(response.tracklist());
    }

    // GET USER RATING FOR A TRACK
    public UserTrackRequestDto userRating(
            UserTrackRequestDto userTrackRequestDto,
            String tadbId,
            UUID userId
    ) {

        Boolean favorite = userTrackRequestDto.favorite();
        Double newRating = userTrackRequestDto.rating();

        User user = userRepository.findById(userId)
                .orElseThrow();

        Tracklist tracklist =
                tracklistRepository.findTracklistByTadbTrackId(tadbId);

        UserTrack userTrack =
                userTrackRepository
                        .getUserTrackByUser_IdAndTracklist_TadbTrackId(
                                userId,
                                tadbId
                        );

        // If no rating was previously created
        if (userTrack == null) {

            userTrack = new UserTrack();

            userTrack.setUser(user);
            userTrack.setTracklist(tracklist);
            userTrack.setCreatedAt(LocalDateTime.now());

            // Only count it if the new rating is actually > 0
            if (newRating != null && newRating > 0) {

                tracklist.setRatingCount(
                        tracklist.getRatingCount() + 1
                );

                tracklist.setRatingSum(
                        tracklist.getRatingSum() + newRating
                );
            }

        } else {

            Double oldRating = userTrack.getRating() != null
                    ? userTrack.getRating()
                    : 0.0;

            // Remove old rating from the aggregate
            if (oldRating > 0) {

                tracklist.setRatingSum(
                        tracklist.getRatingSum() - oldRating
                );

                tracklist.setRatingCount(
                        Math.max(
                                0,
                                tracklist.getRatingCount() - 1
                        )
                );
            }

            // Add new rating to the aggregate
            if (newRating != null && newRating > 0) {

                tracklist.setRatingSum(
                        tracklist.getRatingSum() + newRating
                );

                tracklist.setRatingCount(
                        tracklist.getRatingCount() + 1
                );
            }
        }

        userTrack.setFavorite(favorite);
        userTrack.setRating(newRating);
        userTrack.setUser(user);
        userTrack.setTracklist(tracklist);

        userTrackRepository.save(userTrack);
        tracklistRepository.save(tracklist);

        return new UserTrackRequestDto(
                userTrack.getRating(),
                userTrack.getFavorite()
        );
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
        Double userRating = null;

        if(usertrack != null){
            userRating = usertrack.getRating();
        }

        return new UserTrackResponseDto(userRating,average);

    }

    // GET THE TOP SONG FROM THE ALBUM TRACKLIST
    public String getTopTrack(String mbid) {

        List<String> topTracks =
                tracklistRepository.findTopRatedTrackId(
                        mbid,
                        PageRequest.of(0, 1)
                );

        return topTracks.isEmpty()
                ? null
                : topTracks.get(0);
    }
}
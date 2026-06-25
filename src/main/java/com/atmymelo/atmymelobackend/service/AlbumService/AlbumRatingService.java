package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.config.Exceptions.CustomIllegalStateException;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.QueueDTO.QueueRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.QueueDTO.QueueResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO.RatingRequestDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.RatingDTO.RatingResponseDTO;
import com.atmymelo.atmymelobackend.entity.Album;
import com.atmymelo.atmymelobackend.entity.User;
import com.atmymelo.atmymelobackend.entity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import com.atmymelo.atmymelobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumRatingService {

    private final UserAlbumRepository userAlbumRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    // FETCH ALBUM FROM DB IF NOT CREATE ONE
    private UserAlbum getOrCreate(UUID userId, String mbid) {

        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId, mbid);
        // FETCH USER ALBUM . IF IT DOES NOT EXIST (RETURNS NULL) CREATE ONE .

        if (userAlbum == null) {
            userAlbum = new UserAlbum();
            //CREATE THE USER ALBUM

            Album album = albumRepository.findById(mbid)
                    .orElseThrow(() -> new RuntimeException("Album not found"));
            // FETCH THE ALBUM FROM ALBUM REPO

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            // FETCH THE USER


            userAlbum.setAlbum(album);
            userAlbum.setCreatedAt(LocalDateTime.now());
            userAlbum.setUser(user);
            userAlbum.setRating(0);
            userAlbum.setIsFavorite(false);
            userAlbum.setInQueue(false);
        }

        return userAlbum;
    }

    // ALBUM RATING
    public RatingResponseDTO rate(RatingRequestDTO rateDto, UUID userId, String mbid) {

        UserAlbum userAlbum = getOrCreate(userId, mbid);
        // GET THE USER-ALBUM IF DOES NOT EXIST CREATE

        if (rateDto.rating() > 0) userAlbum.setInQueue(false);
        // IF RATED THEN SET QUEUE = FALSE .

        userAlbum.setRating(rateDto.rating());
        // SET THE RATING TO THE USER-ALBUM

        userAlbumRepository.save(userAlbum);

        return new RatingResponseDTO(userAlbum.getAlbum(), userAlbum.getRating());
    }

    // FAVORITE THE ALBUM
    public FavoriteResponseDTO favorite(FavoriteRequestDTO favDto, UUID userId, String mbid) {

        UserAlbum userAlbum = getOrCreate(userId, mbid);
        // GET THE USER-ALBUM IF DOES NOT EXIST CREATE

        userAlbum.setIsFavorite(favDto.favorite());
        // SET THE ALBUM TO FAVORITE

        if (Boolean.TRUE.equals(userAlbum.getIsFavorite())) {
            userAlbum.setInQueue(false);
        }
        // IF ALBUM IS TRUE THEN REMOVE FROM QUEUE .

        userAlbumRepository.save(userAlbum);

        return new FavoriteResponseDTO(userAlbum.getAlbum(), userAlbum.getIsFavorite());
    }

    // QUEUE THE ALBUM
    public QueueResponseDTO queue(QueueRequestDTO queueDto, UUID userId, String mbid) {

        UserAlbum userAlbum = getOrCreate(userId, mbid);
        // GET THE USER-ALBUM IF DOES NOT EXIST CREATE

        boolean isFavorite = Boolean.TRUE.equals(userAlbum.getIsFavorite());
        int rating = userAlbum.getRating() != null ? userAlbum.getRating() : 0;

        if (queueDto.queue() && (isFavorite || rating > 0)) {
            throw new CustomIllegalStateException("Album has been marked reviewed!");
        }
        // CANNOT QUEUE IF IT HAS ACTIVITY ON IT .

        userAlbum.setInQueue(queueDto.queue());
        // SET QUEUE

        userAlbumRepository.save(userAlbum);

        return new QueueResponseDTO(userAlbum.getAlbum(), userAlbum.getInQueue());
    }
}
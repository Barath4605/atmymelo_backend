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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumRatingService {

    private final UserAlbumRepository userAlbumRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    // RATING THE ALBUM , FROM 1 to 5
    public RatingResponseDTO rate(RatingRequestDTO rateDto, UUID userId, String mbid) {

        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId, mbid);
        if (userAlbum == null) {

            userAlbum = new UserAlbum();

            Album album = albumRepository.findById(mbid)
                    .orElseThrow(() -> new RuntimeException("Album not found"));

            userAlbum.setAlbum(album);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userAlbum.setUser(user);
        }

        if(rateDto.rating() > 0) userAlbum.setInQueue(false);

        userAlbum.setRating(rateDto.rating());

        userAlbumRepository.save(userAlbum);

        return new RatingResponseDTO(userAlbum.getAlbum(), userAlbum.getRating());
    }

    // ADDING THE ALBUMS TO FAVORITES
    public FavoriteResponseDTO favorite(FavoriteRequestDTO favDto, UUID userId, String mbid) {
        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId, mbid);

        if (userAlbum == null) {

            userAlbum = new UserAlbum();

            Album album = albumRepository.findById(mbid)
                    .orElseThrow(() -> new RuntimeException("Album not found"));

            userAlbum.setAlbum(album);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userAlbum.setUser(user);
        }

        userAlbum.setIsFavorite(favDto.favorite());

        if(userAlbum.getIsFavorite()) userAlbum.setInQueue(false);

        userAlbumRepository.save(userAlbum);

        return new FavoriteResponseDTO(userAlbum.getAlbum(), userAlbum.getIsFavorite());

    }

    // ADDING ALBUMS TO QUEUE
    public QueueResponseDTO queue(QueueRequestDTO queueDto, UUID userId, String mbid) {
        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId, mbid);

        if (userAlbum == null) {

            userAlbum = new UserAlbum();

            Album album = albumRepository.findById(mbid)
                    .orElseThrow(() -> new RuntimeException("Album not found"));
            userAlbum.setAlbum(album);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userAlbum.setUser(user);
        }

        if(queueDto.queue() && (userAlbum.getIsFavorite() || userAlbum.getRating() > 0)) {
            throw new CustomIllegalStateException("Album has been marked reviewed!");
        }

        userAlbum.setInQueue(queueDto.queue());

        userAlbumRepository.save(userAlbum);

        return new QueueResponseDTO(userAlbum.getAlbum(), userAlbum.getInQueue());

    }
}

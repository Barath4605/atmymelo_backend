package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteGenreResponseDTO;
import com.atmymelo.atmymelobackend.entity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final UserAlbumRepository userAlbumRepository;

    public List<String> getUserQueueGenres(UUID userId) {
        return userAlbumRepository.findUserQueueGenres(userId);
    }

    public List<FavoriteGenreResponseDTO> getQueueAlbumsByGenre(UUID userId, String genre) {

        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Genre is required");
        }

        List<UserAlbum> albums =
                userAlbumRepository.findQueueByGenre(userId, genre);

        return albums.stream()
                .map(ua -> new FavoriteGenreResponseDTO(
                        ua.getAlbum().getId(),
                        ua.getAlbum().getTitle(),
                        ua.getAlbum().getArtist().getName(),
                        ua.getRating(),
                        ua.getAlbum().getReleaseYear(),
                        ua.getAlbum().getImageUrl()
                ))
                .toList();
    }
}

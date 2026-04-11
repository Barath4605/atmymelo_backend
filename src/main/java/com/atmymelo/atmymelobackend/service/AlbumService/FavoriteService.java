package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteGenreResponseDTO;
import com.atmymelo.atmymelobackend.entity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import com.atmymelo.atmymelobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserRepository userRepository;
    private final UserAlbumRepository userAlbumRepository;

    public List<String> getUserGenres(UUID userId) {
        List<String> genres = userAlbumRepository.findUserFavoriteGenres(userId);

        return genres.stream()
                .map(genre -> genre == null ? "Other" : genre)
                .toList();
    }

    public List<FavoriteGenreResponseDTO> getFavoriteAlbumOnGenre(UUID userId, String genre) {
        List<UserAlbum> albums = userAlbumRepository.findFavoritesByGenre(userId, genre);

        return albums.stream()
                .map(ua -> new FavoriteGenreResponseDTO(
                        ua.getAlbum().getId(),
                        ua.getAlbum().getTitle(),
                        ua.getAlbum().getArtist().getName(),
                        ua.getRating(),
                        ua.getAlbum().getReleaseYear(),
                        ua.getAlbum().getImageUrl()
                )).toList();

    }

}

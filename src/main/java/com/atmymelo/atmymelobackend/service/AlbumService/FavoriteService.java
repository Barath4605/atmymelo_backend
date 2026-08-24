package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteGenreResponseDTO;
import com.atmymelo.atmymelobackend.entity.AlbumEntity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import com.atmymelo.atmymelobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserAlbumRepository userAlbumRepository;


    // FETCH ALL THE GENRES OF THE USER (IN FAVS)
    public List<String> getUserGenres(UUID userId) {
        List<String> genres = userAlbumRepository.findUserFavoriteGenres(userId);
        String topUserGenre = genres.get(0);

        return genres.stream()
                .map(genre -> genre == null ? "Other" : genre)
                .toList();
    }

    // GET USER'S TOP GENRE
    public String getUserTopGenre(UUID userId) {
        List<String> genres = userAlbumRepository.findUserFavoriteGenres(userId);

        if(genres.isEmpty()) return "";

        int random = ThreadLocalRandom.current().nextInt(genres.size());
        return genres.get(random);
    }

    public List<FavoriteGenreResponseDTO> getFavoriteAlbumOnGenre(UUID userId, String genre) {
        List<UserAlbum> albums = userAlbumRepository.findFavoritesByGenre(userId, genre);

        return albums.stream()
                .map(ua -> new FavoriteGenreResponseDTO(

                        // ALBUM
                        ua.getAlbum().getId(),
                        ua.getAlbum().getTitle(),
                        ua.getAlbum().getReleaseYear(),
                        ua.getAlbum().getImageUrl(),

                        // ARTIST
                        ua.getAlbum().getArtist().getName(),
                        ua.getAlbum().getArtist().getId(),

                        // USER
                        ua.getRating()
                )).toList();

    }

}

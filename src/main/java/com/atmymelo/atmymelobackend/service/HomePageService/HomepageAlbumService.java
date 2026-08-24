package com.atmymelo.atmymelobackend.service.HomePageService;

import com.atmymelo.atmymelobackend.dto.HomePageDTOs.AlbumsFromUserTopGenreDto;
import com.atmymelo.atmymelobackend.dto.HomePageDTOs.RelistenAlbumDto;
import com.atmymelo.atmymelobackend.entity.AlbumEntity.Album;
import com.atmymelo.atmymelobackend.entity.AlbumEntity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import com.atmymelo.atmymelobackend.service.AlbumService.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HomepageAlbumService {

    private final UserAlbumRepository userAlbumRepository;
    private final AlbumRepository albumRepository;
    private final FavoriteService favoriteService;

    // SUGGEST USER TO RE LISTEN TO THESE ALBUMS
    public List<RelistenAlbumDto> suggestRelisten(UUID userId) {

        List<UserAlbum> userAlbums = userAlbumRepository.findTop5ByUserIdOrderByUpdatedAtDesc(userId);

        return userAlbums.stream().map(userAlbum -> new RelistenAlbumDto(
                userAlbum.getUpdatedAt(),

                // ALBUM
                userAlbum.getAlbum().getId(),
                userAlbum.getAlbum().getTitle(),
                userAlbum.getAlbum().getImageUrl(),

                // ARTIST
                userAlbum.getAlbum().getArtist().getId(),
                userAlbum.getAlbum().getArtist().getName()
        )).toList();
    }

    // SUGGEST USER WITH ALBUMS FROM THEIR TOP GENRE
    public List<AlbumsFromUserTopGenreDto> getAlbumsFromUserTopGenre(UUID userId) {

        String genre = favoriteService.getUserTopGenre(userId);

        List<Album> albums = albumRepository.findByGenre(genre);

        Collections.shuffle(albums);

        albums = albums.stream()
                .limit(5)
                .toList();

        return albums.stream()
                .map(album -> new AlbumsFromUserTopGenreDto(

                        // GENRE
                        genre,

                        // ALBUM
                        album.getId(),
                        album.getTitle(),
                        album.getImageUrl(),

                        // ARTIST
                        album.getArtist().getId(),
                        album.getArtist().getName()

                )
        ).toList();
    }

}

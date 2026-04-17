package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ArtistAlbumsDTO.AudioDbAlbumDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.ArtistAlbumsDTO.AudioDbResponseDTO;
import com.atmymelo.atmymelobackend.entity.Album;
import com.atmymelo.atmymelobackend.entity.Artist;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
import com.atmymelo.atmymelobackend.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistAlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final AudioDbClient audioDbClient;

    public List<Album> getArtistAlbums(String mbid) {

        Artist artist = artistRepository.findById(mbid)
                .orElseThrow();

        importAlbums(artist);

        return albumRepository.findByArtistId(mbid);
    }

    private void importAlbums(Artist artist) {

        String tadbId = artist.getTadbArtistId();

        AudioDbResponseDTO response = audioDbClient.fetchAlbums(tadbId);

        if (response == null || response.album() == null) return;

        List<Album> albums = response.album().stream()

                // only real albums
                .filter(dto -> "album".equalsIgnoreCase(dto.strReleaseFormat()))

                // map DTO → entity
                .map(dto -> mapToEntity(dto, artist))

                // remove nulls (duplicate skips)
                .filter(Objects::nonNull)

                .toList();

        albumRepository.saveAll(albums);
    }

    private Album mapToEntity(AudioDbAlbumDTO dto, Artist artist) {

        if (dto.strMusicBrainzID() == null) return null;

        Album album = albumRepository.findById(dto.strMusicBrainzID())
                .orElse(new Album());

        album.setId(dto.strMusicBrainzID());

        album.setTadbAlbumId(dto.idAlbum());

        album.setTitle(dto.strAlbum());
        album.setImageUrl(dto.strAlbumThumb());
        album.setGenre(dto.strGenre());

        if (dto.strDescription() != null && !dto.strDescription().isBlank()) {
            album.setDescription(dto.strDescription());
        }

        album.setArtist(artist);

        if (dto.intYearReleased() != null) {
            try {
                album.setReleaseYear(Integer.parseInt(dto.intYearReleased()));
            } catch (Exception ignored) {}
        }

        return album;
    }
}
package com.atmymelo.atmymelobackend.service.AlbumService;

import com.atmymelo.atmymelobackend.config.Exceptions.CustomRuntimeException;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.AlbumResponseDTO;
import com.atmymelo.atmymelobackend.dto.AlbumDTOs.AlbumSearchDTO;
import com.atmymelo.atmymelobackend.entity.Album;
import com.atmymelo.atmymelobackend.entity.Artist;
import com.atmymelo.atmymelobackend.entity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.AlbumRepository;
import com.atmymelo.atmymelobackend.repository.ArtistRepository;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final RestTemplate restTemplate;
    private final AlbumRepository  albumRepository;
    private final ArtistRepository artistRepository;
    private final UserAlbumRepository userAlbumRepository;

    // SEARCH ALL THE ALBUMS.
    public List<AlbumSearchDTO> searchAlbums(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

            String url = "https://musicbrainz.org/ws/2/release-group/?query="
                    + encodedQuery
                    + "&type=album"
                    + "&limit=15"
                    + "&fmt=json";

            Map response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.get("release-groups") == null) {
                return List.of();
            }

            List<Map<String, Object>> groups =
                    (List<Map<String, Object>>) response.get("release-groups");

            return groups.stream()
                    .filter(g -> {
                        // 1. Drop low-confidence matches
                        Object scoreObj = g.get("score");
                        if (scoreObj == null) return false;
                        int score = Integer.parseInt(scoreObj.toString());
                        return score >= 80;
                    })
                    .filter(g -> {
                        // 2. Drop entries with no release date (usually junk/incomplete)
                        String date = (String) g.get("first-release-date");
                        return date != null && !date.isBlank();
                    })
                    .sorted((a, b) -> {
                        // 3. Sort by score descending (make it explicit)
                        int scoreA = Integer.parseInt(a.get("score").toString());
                        int scoreB = Integer.parseInt(b.get("score").toString());
                        return Integer.compare(scoreB, scoreA);
                    })
                    .limit(10)
                    .map(g -> {
                        String id = (String) g.get("id");
                        String title = (String) g.get("title");

                        String artist = "Unknown";
                        List<Map<String, Object>> artistCredit =
                                (List<Map<String, Object>>) g.get("artist-credit");

                        if (artistCredit != null && !artistCredit.isEmpty()) {
                            Map<String, Object> credit = artistCredit.get(0);
                            Object nameObj = credit.get("name");
                            if (nameObj instanceof String) {
                                artist = (String) nameObj;
                            } else {
                                Map<String, Object> artistObj = (Map<String, Object>) credit.get("artist");
                                if (artistObj != null) {
                                    artist = (String) artistObj.get("name");
                                }
                            }
                        }

                        String date = (String) g.get("first-release-date");
                        String year = (date != null && date.length() >= 4)
                                ? date.substring(0, 4)
                                : null;

                        return new AlbumSearchDTO(id, title, artist, year);
                    })
                    .toList();

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // OPEN THE ALBUM PAGE USING THE MBID FROM THE SEARCH RESULTS FROM THE AUDIO DB API
    public AlbumResponseDTO getAlbumByMbid(String mbid, UUID userId) {

        Optional<Album> existing = albumRepository.findById(mbid);
        if (existing.isPresent()) {
            UserAlbum userAlbum =
                    userAlbumRepository.findByUserIdAndAlbumId(userId, mbid);

            Integer rating = 0;
            Boolean isFavorite = false;
            Boolean inQueue = false;

            if (userAlbum != null) {
                rating = userAlbum.getRating();
                if(rating == null) {
                    rating = 0;
                }
                isFavorite = userAlbum.getIsFavorite();
                inQueue = userAlbum.getInQueue();
            }

            return new AlbumResponseDTO(existing.get(), rating, isFavorite, inQueue);
        }

        String albumUrl = "https://www.theaudiodb.com/api/v1/json/123/album-mb.php?i=" + mbid;
        Map albumResponse = restTemplate.getForObject(albumUrl, Map.class);

        if (albumResponse == null || albumResponse.get("album") == null) {
            throw new CustomRuntimeException("Album not found");
        }

        List<Map<String, Object>> albums =
                (List<Map<String, Object>>) albumResponse.get("album");

        if (albums.isEmpty()) {
            throw new CustomRuntimeException("Album not found");
        }

        Map<String, Object> data = albums.get(0);

        Artist artist = null;
        String artistMbid = (String) data.get("strMusicBrainzArtistID");

        if (artistMbid != null && !artistMbid.isBlank()) {

            Optional<Artist> existingArtist = artistRepository.findById(artistMbid);

            if (existingArtist.isPresent()) {
                artist = existingArtist.get();
            } else {
                String artistUrl = "https://www.theaudiodb.com/api/v1/json/123/artist-mb.php?i=" + artistMbid;
                Map artistResponse = restTemplate.getForObject(artistUrl, Map.class);

                if (artistResponse != null && artistResponse.get("artists") != null) {
                    List<Map<String, Object>> artists =
                            (List<Map<String, Object>>) artistResponse.get("artists");

                    if (!artists.isEmpty()) {
                        Map<String, Object> artistData = artists.get(0);

                        // CREATE ARTIST AND STORE THE DATA

                        Artist newArtist = new Artist();
                        newArtist.setId(artistMbid);
                        newArtist.setName((String) artistData.get("strArtist"));
                        newArtist.setBio((String) artistData.get("strBiography"));
                        newArtist.setTadbArtistId((String) artistData.get("idArtist"));
                        newArtist.setBackdropUrl((String) artistData.get("strArtistFanart"));
                        newArtist.setLogoUrl((String) artistData.get("strArtistLogo"));
                        newArtist.setPhotoUrl((String) artistData.get("strArtistThumb"));
                        newArtist.setLabel((String) artistData.get("strLabel"));
                        newArtist.setBorn((String) artistData.get("intBornYear"));
                        newArtist.setFormed((String) artistData.get("intFormedYear"));

                        artist = artistRepository.save(newArtist);
                    }
                }
            }
        }

        // CREATE ALBUM AND STORE THE DATA

        String desc = (String) data.get("strDescription");
        String genre = (String) data.get("strGenre");

        if (desc == null || desc.isBlank()) {
            desc = "No description available";
        }

        if (genre == null || genre.isBlank()) {
            genre = "Other";
        }

        Album album = new Album();
        album.setId(mbid);
        album.setTitle((String) data.get("strAlbum"));
        album.setGenre(genre);
        album.setTadbAlbumId((String) data.get("idAlbum"));
        album.setDescription(desc);
        album.setImageUrl((String) data.get("strAlbumThumb"));
        album.setArtist(artist);

        String year = (String) data.get("intYearReleased");
        if (year != null && !year.isBlank()) {
            try {
                album.setReleaseYear(Integer.parseInt(year));
            } catch (Exception ignored) {}
        }

        albumRepository.save(album);
        UserAlbum userAlbum = userAlbumRepository.findByUserIdAndAlbumId(userId, mbid);

        Integer rating = 0;
        Boolean isFavorite = false;
        Boolean inQueue = false;

        if (userAlbum != null) {
            if (userAlbum.getRating() != null)
                rating = userAlbum.getRating();

            if (userAlbum.getIsFavorite() != null)
                isFavorite = userAlbum.getIsFavorite();

            if (userAlbum.getInQueue() != null)
                inQueue = userAlbum.getInQueue();
        }

        return new AlbumResponseDTO(album, rating, isFavorite, inQueue);
    }

}
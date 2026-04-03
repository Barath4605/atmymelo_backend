package com.atmymelo.atmymelobackend.service.ArtistService;

import com.atmymelo.atmymelobackend.dto.ArtistSearchResponseDTO;
import com.atmymelo.atmymelobackend.entity.Artist;
import com.atmymelo.atmymelobackend.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    @Value("${fanart.api.key}")
    private String FANART_API_KEY;

    private final ArtistRepository artistRepository;
    private final RestTemplate restTemplate;

    //  SEARCHING ALL THE ARTISTS USING THE MB API
    public List<ArtistSearchResponseDTO> searchArtists(String query) {

        String url = "https://musicbrainz.org/ws/2/artist/?query=" + query + "&fmt=json";

        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> artists = (List<Map<String, Object>>) response.get("artists");

        return artists.stream()
                .limit(5)
                .map(a -> {
                    String id = (String) a.get("id");
                    String name = (String) a.get("name");
                    String country = (String) a.get("country");

                    String imageUrl = fetchFanartImage(id);

                    return new ArtistSearchResponseDTO(id, name, country, imageUrl);
                })
                .toList();
    }

    // OPENING THE DEDICATED ARTIST PAGE WITH THE HELP OF MBID FETCHED FROM THE SEARCH RESULTS
    public Artist getArtistByMbid(String mbid) {

        Optional<Artist> existing = artistRepository.findById(mbid);
        if (existing.isPresent()) {
            return existing.get();
        }

        String url = "https://theaudiodb.com/api/v1/json/2/artist-mb.php?i=" + mbid;

        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> artists = (List<Map<String, Object>>) response.get("artists");

        if (artists == null || artists.isEmpty()) {
            throw new RuntimeException("Artist not found");
        }

        Map<String, Object> data = artists.get(0);

        Artist artist = new Artist();
        artist.setId(mbid);
        artist.setName((String) data.get("strArtist"));
        artist.setTadbArtistId((String) data.get("idArtist"));
        artist.setBio((String) data.get("strBiography"));
        artist.setPhotoUrl((String) data.get("strArtistThumb"));
        artist.setLogoUrl((String) data.get("strArtistLogo"));
        artist.setBackdropUrl((String) data.get("strArtistFanart"));
        artist.setLabel((String) data.get("strLabel"));
        artist.setBorn((String) data.get("intBornYear"));
        artist.setFormed((String) data.get("intFormedYear"));

        return artistRepository.save(artist);
    }

    // ====== HELPER FUNCTIONS ======

    // GETTING THE FANART IMAGES FOR THE ARTIST FROM THE MBID
    private String fetchFanartImage(String mbid) {

        try {
            String url = "https://webservice.fanart.tv/v3/music/"
                    + mbid + "?api_key=" + FANART_API_KEY;

            Map response = restTemplate.getForObject(url, Map.class);

            if (response == null) return null;

            // 🔹 1. try artistthumb
            List<Map<String, Object>> images =
                    (List<Map<String, Object>>) response.get("artistthumb");

            if (images != null && !images.isEmpty()) {
                return images.stream()
                        .max(Comparator.comparingInt(a -> parseLikes(a.get("likes"))))
                        .map(img -> (String) img.get("url"))
                        .orElse(null);
            }

            Map<String, Object> albums = (Map<String, Object>) response.get("albums");

            if (albums != null && !albums.isEmpty()) {

                for (Object albumObj : albums.values()) {

                    Map<String, Object> album = (Map<String, Object>) albumObj;

                    List<Map<String, Object>> covers =
                            (List<Map<String, Object>>) album.get("albumcover");

                    if (covers != null && !covers.isEmpty()) {
                        return (String) covers.get(0).get("url");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Fanart error: " + e.getMessage());
        }

        return null;
    }

    // GETTING ARTISTS BASED ON THEIR LIKES
    private int parseLikes(Object likes) {
        try {
            return Integer.parseInt((String) likes);
        } catch (Exception e) {
            return 0;
        }
    }
}
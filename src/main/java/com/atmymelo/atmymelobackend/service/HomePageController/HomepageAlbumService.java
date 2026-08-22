package com.atmymelo.atmymelobackend.service.HomePageController;

import com.atmymelo.atmymelobackend.dto.HomePageDTOs.RelistenAlbumDto;
import com.atmymelo.atmymelobackend.entity.AlbumEntity.UserAlbum;
import com.atmymelo.atmymelobackend.repository.UserAlbumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HomepageAlbumService {

    private final UserAlbumRepository userAlbumRepository;

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

}

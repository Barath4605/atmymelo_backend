package com.atmymelo.atmymelobackend.controller.AlbumController;

import com.atmymelo.atmymelobackend.dto.AlbumDTOs.FavoriteDTO.FavoriteGenreResponseDTO;
import com.atmymelo.atmymelobackend.service.AlbumService.FavoriteService;
import com.atmymelo.atmymelobackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoriteService favoriteService;
    private final JwtUtil jwtUtil;

    @GetMapping("/genre")
    public List<String> getUserGenres(@RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtUtil.extractUserId(authHeader);

        return favoriteService.getUserGenres(userId);
    }

    @GetMapping("/albums-in-genre")
    public List<FavoriteGenreResponseDTO> getFavoriteAlbumOnGenre(@RequestHeader("Authorization") String authHeader,
                                                                  @RequestParam String genre) {
        UUID user =  jwtUtil.extractUserId(authHeader);

        return favoriteService.getFavoriteAlbumOnGenre(user, genre);
    }

}

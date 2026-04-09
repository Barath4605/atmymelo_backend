package com.atmymelo.atmymelobackend.controller.ProfileController;

import com.atmymelo.atmymelobackend.dto.ProfileDTO.ProfileResponseDTO;
import com.atmymelo.atmymelobackend.service.ProfileService.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponseDTO getProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }

}

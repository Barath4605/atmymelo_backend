package com.atmymelo.atmymelobackend.service.ProfileService;

import com.atmymelo.atmymelobackend.config.Exceptions.CustomIllegalStateException;
import com.atmymelo.atmymelobackend.dto.ProfileDTO.ProfileResponseDTO;
import com.atmymelo.atmymelobackend.entity.User;
import com.atmymelo.atmymelobackend.repository.ReviewRepository;
import com.atmymelo.atmymelobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ProfileResponseDTO getProfile(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomIllegalStateException("User Not Found!"));

        user.setBio("");
        return new ProfileResponseDTO(user.getId(), user.getUsername(), user.getName(),
                user.getBio(), user.getCreatedAt());

    }
}

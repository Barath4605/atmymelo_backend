package com.atmymelo.atmymelobackend.service.UserServices;

import com.atmymelo.atmymelobackend.config.Exceptions.CustomRuntimeException;
import com.atmymelo.atmymelobackend.dto.UserSigningDTOs.UserRegisterRequestDTO;
import com.atmymelo.atmymelobackend.dto.UserSigningDTOs.UserRegisterResponseDTO;
import com.atmymelo.atmymelobackend.entity.ROLE;
import com.atmymelo.atmymelobackend.entity.User;
import com.atmymelo.atmymelobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {

        User user = new User();

        if(userRepository.existsByEmail(userRegisterRequestDTO.email())) {
            throw new CustomRuntimeException("Email already exists");
        }

        user.setUsername(userRegisterRequestDTO.username());
        user.setEmail(userRegisterRequestDTO.email());
        user.setPasswordHash(passwordEncoder.encode(userRegisterRequestDTO.password()));
        user.setRole(ROLE.USER);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new UserRegisterResponseDTO(user.getUsername(), user.getEmail(), user.getId());
    }

}


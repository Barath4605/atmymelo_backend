package com.atmymelo.atmymelobackend.service.UserServices;

import com.atmymelo.atmymelobackend.config.Exceptions.CustomIllegalArgumentException;
import com.atmymelo.atmymelobackend.dto.UserDTOs.UserLoginRequestDTO;
import com.atmymelo.atmymelobackend.dto.UserDTOs.UserLoginResponseDTO;
import com.atmymelo.atmymelobackend.entity.User;
import com.atmymelo.atmymelobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLoginResponseDTO login(UserLoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CustomIllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(dto.password(), user.getPasswordHash())) {
            throw new CustomIllegalArgumentException("Invalid credentials");
        }


        return new UserLoginResponseDTO("Login Success", user.getUsername(), user.getId());
    }
}

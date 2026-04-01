package com.atmymelo.atmymelobackend.controller.UserController;

import com.atmymelo.atmymelobackend.dto.UserSigningDTOs.UserRegisterRequestDTO;
import com.atmymelo.atmymelobackend.dto.UserSigningDTOs.UserRegisterResponseDTO;
import com.atmymelo.atmymelobackend.service.UserServices.UserRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponseDTO registerUser(
            @Valid @RequestBody UserRegisterRequestDTO dto) {

        return userRegisterService.register(dto);
    }

}

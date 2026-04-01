package com.atmymelo.atmymelobackend.controller.UserController;

import com.atmymelo.atmymelobackend.dto.UserSigningDTOs.UserLoginRequestDTO;
import com.atmymelo.atmymelobackend.dto.UserSigningDTOs.UserLoginResponseDTO;
import com.atmymelo.atmymelobackend.service.UserServices.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO dto) {
        return ResponseEntity.ok(userLoginService.login(dto));
    }

}

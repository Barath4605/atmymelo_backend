package com.atmymelo.atmymelobackend.config;

import com.atmymelo.atmymelobackend.config.Exceptions.CustomIllegalArgumentException;
import com.atmymelo.atmymelobackend.config.Exceptions.CustomIllegalStateException;
import com.atmymelo.atmymelobackend.config.Exceptions.CustomRuntimeException;
import com.atmymelo.atmymelobackend.dto.ExceptionDTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserExists(CustomRuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(ex.getMessage())).getBody();
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handleInvalidCredentialsException(CustomIllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(ex.getMessage())).getBody();
    }

    @ExceptionHandler(CustomIllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleAlbumReviewedException(CustomIllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(ex.getMessage())).getBody();
    }

}

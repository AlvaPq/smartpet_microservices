package com.mascota.service.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.mascota.service.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            BusinessException.class)
    public ResponseEntity<ErrorResponse>
            handleBusiness(
                    BusinessException ex) {

        ErrorResponse error =
                ErrorResponse.builder()
                        .mensaje(
                                ex.getMessage())
                        .status(400)
                        .build();

        return ResponseEntity
                .badRequest()
                .body(error);
    }

}
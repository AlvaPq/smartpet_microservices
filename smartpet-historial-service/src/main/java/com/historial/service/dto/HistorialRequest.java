package com.historial.service.dto;

import jakarta.validation.constraints.*;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialRequest {

    @NotNull
    private Integer citaId;

    @NotBlank
    private String diagnostico;

    @NotBlank
    private String tratamiento;

    private String observaciones;
}
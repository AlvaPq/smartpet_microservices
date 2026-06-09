package com.cita.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaRequest {

    @NotNull
    private LocalDate fecha;

    @NotNull
    private LocalTime hora;

    @NotBlank
    private String motivo;

    @NotNull
    private Integer clienteId;

    @NotNull
    private Integer mascotaId;

    @NotNull
    private Integer veterinarioId;

}
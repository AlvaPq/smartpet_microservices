package com.historial.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaResponse {

    private Integer id;

    private LocalDate fecha;

    private LocalTime hora;

    private String motivo;

    private String estado;

    private Integer clienteId;

    private Integer mascotaId;

    private Integer veterinarioId;
}
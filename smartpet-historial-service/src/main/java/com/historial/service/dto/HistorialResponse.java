package com.historial.service.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialResponse {

    private Integer id;

    private Integer mascotaId;

    private String nombreMascota;

    private Integer veterinarioId;

    private String nombreVeterinario;

    private Integer citaId;

    private String diagnostico;

    private String tratamiento;

    private String observaciones;

    private LocalDate fechaRegistro;
}
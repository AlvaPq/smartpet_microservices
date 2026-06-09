package com.cita.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cita.service.entity.enums.EstadoCita;

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

    private EstadoCita estado;

    private Integer clienteId;

    private Integer mascotaId;

    private Integer veterinarioId;
    
    private String nombreCliente;

    private String nombreMascota;

    private String nombreVeterinario;

}
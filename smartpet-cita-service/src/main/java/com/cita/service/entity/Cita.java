package com.cita.service.entity;
import java.time.LocalDate;
import java.time.LocalTime;

import com.cita.service.entity.enums.EstadoCita;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fecha;

    private LocalTime hora;

    private String motivo;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado;

    private Integer clienteId;

    private Integer mascotaId;

    private Integer veterinarioId;

}
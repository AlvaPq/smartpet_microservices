package com.historial.service.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "historial_clinico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer mascotaId;

    private Integer citaId;

    private Integer veterinarioId;

    @Column(length = 1000)
    private String diagnostico;

    @Column(length = 1000)
    private String tratamiento;

    @Column(length = 1000)
    private String observaciones;

    private LocalDate fechaRegistro;
}
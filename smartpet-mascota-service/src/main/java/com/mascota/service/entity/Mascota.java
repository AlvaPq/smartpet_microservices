package com.mascota.service.entity;

import java.time.LocalDate;

import com.mascota.service.entity.enums.Especie;
import com.mascota.service.entity.enums.SexoMascota;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mascota {

    @Id
    @GeneratedValue(
            strategy =
            GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Especie especie;

    @Enumerated(EnumType.STRING)
    private SexoMascota sexo;

    private String raza;

    private LocalDate fechaNacimiento;

    private Double peso;

    private Boolean estado;

    private Integer clienteId;

}
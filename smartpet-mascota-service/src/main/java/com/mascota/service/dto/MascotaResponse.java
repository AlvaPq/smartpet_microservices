package com.mascota.service.dto;

import java.time.LocalDate;

import com.mascota.service.entity.enums.Especie;
import com.mascota.service.entity.enums.SexoMascota;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaResponse {

    private Integer id;

    private String nombre;

    private Especie especie;

    private SexoMascota sexo;

private String raza;

    private LocalDate fechaNacimiento;

    private Double peso;

    private Boolean estado;

    private Integer clienteId;
    
    private String nombreCliente;

}
package com.mascota.service.dto;

import java.time.LocalDate;

import com.mascota.service.entity.enums.Especie;
import com.mascota.service.entity.enums.SexoMascota;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaRequest {

    @NotBlank
    private String nombre;

    
    @NotNull
    private Especie especie;

    @NotNull
    private SexoMascota sexo;
    
private String raza;


    private LocalDate fechaNacimiento;

    private Double peso;

    private Integer clienteId;

}
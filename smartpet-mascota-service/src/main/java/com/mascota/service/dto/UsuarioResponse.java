package com.mascota.service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Integer id;

    private String nombre;

    private String apellido;

    private String correo;

    private String rol;

    private Boolean estado;
}
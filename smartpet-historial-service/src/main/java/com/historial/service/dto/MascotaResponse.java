package com.historial.service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MascotaResponse {

    private Integer id;

    private String nombre;

    private Integer clienteId;

    private String nombreCliente;

    private Boolean estado;
}
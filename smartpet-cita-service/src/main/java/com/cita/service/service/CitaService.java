package com.cita.service.service;

import java.util.List;

import com.cita.service.dto.CitaRequest;
import com.cita.service.dto.CitaResponse;
import com.cita.service.entity.enums.EstadoCita;

public interface CitaService {

    CitaResponse registrar(
            CitaRequest request);

    List<CitaResponse> listar();

    CitaResponse buscarPorId(
            Integer id);

    CitaResponse actualizar(
            Integer id,
            CitaRequest request);

    void eliminar(
            Integer id);
    
    void confirmar(Integer id);
    
    void atender(Integer id);
    
    List<CitaResponse>
    buscarPorEstado(
            EstadoCita estado);

}
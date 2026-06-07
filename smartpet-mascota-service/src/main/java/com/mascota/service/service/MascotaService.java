package com.mascota.service.service;

import java.util.List;

import com.mascota.service.dto.*;

public interface MascotaService {

    MascotaResponse registrar(
            MascotaRequest request);

    List<MascotaResponse> listar();

    MascotaResponse buscarPorId(
            Integer id);

    MascotaResponse actualizar(
            Integer id,
            MascotaRequest request);

    void eliminar(
            Integer id);
    
    List<MascotaResponse>
    buscarPorCliente(
            Integer clienteId);
    
    void activar(Integer id);

}
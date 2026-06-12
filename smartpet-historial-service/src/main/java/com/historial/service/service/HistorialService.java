package com.historial.service.service;

import java.util.List;

import com.historial.service.dto.HistorialRequest;
import com.historial.service.dto.HistorialResponse;

public interface HistorialService {

    HistorialResponse registrar(
            HistorialRequest request);

    List<HistorialResponse> listar();

    HistorialResponse buscarPorId(
            Integer id);

    List<HistorialResponse>
            buscarPorMascota(
                    Integer mascotaId);
}
package com.historial.service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.historial.service.dto.CitaResponse;
import com.historial.service.dto.HistorialRequest;
import com.historial.service.dto.HistorialResponse;
import com.historial.service.dto.MascotaResponse;
import com.historial.service.dto.UsuarioResponse;
import com.historial.service.entity.HistorialClinico;
import com.historial.service.exception.BusinessException;
import com.historial.service.feign.CitaFeignClient;
import com.historial.service.feign.MascotaFeignClient;
import com.historial.service.feign.UsuarioFeignClient;
import com.historial.service.repository.HistorialRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HistorialServiceImpl
        implements HistorialService {

    private final HistorialRepository historialRepository;

    private final MascotaFeignClient mascotaFeignClient;

    private final UsuarioFeignClient usuarioFeignClient;

    private final CitaFeignClient citaFeignClient;

    @Override
    public HistorialResponse registrar(
            HistorialRequest request) {

        CitaResponse cita =
                citaFeignClient.buscarPorId(
                        request.getCitaId());

        if (!"ATENDIDA".equals(
                cita.getEstado())) {

            throw new BusinessException(
                    "Solo las citas atendidas pueden generar historial");
        }

        MascotaResponse mascota =
                mascotaFeignClient.buscarPorId(
                        cita.getMascotaId());

        UsuarioResponse veterinario =
                usuarioFeignClient.buscarPorId(
                        cita.getVeterinarioId());

        validarDatosHistorial(
                mascota,
                veterinario);

        HistorialClinico historial =
                HistorialClinico.builder()
                        .citaId(
                                cita.getId())
                        .mascotaId(
                                cita.getMascotaId())
                        .veterinarioId(
                                cita.getVeterinarioId())
                        .diagnostico(
                                request.getDiagnostico())
                        .tratamiento(
                                request.getTratamiento())
                        .observaciones(
                                request.getObservaciones())
                        .fechaRegistro(
                                LocalDate.now())
                        .build();

        HistorialClinico guardado =
                historialRepository.save(
                        historial);

        return convertirResponse(
                guardado,
                mascota,
                veterinario);
    }

    @Override
    public List<HistorialResponse> listar() {

        return historialRepository.findAll()
                .stream()
                .map(historial -> {

                    MascotaResponse mascota =
                            mascotaFeignClient.buscarPorId(
                                    historial.getMascotaId());

                    UsuarioResponse veterinario =
                            usuarioFeignClient.buscarPorId(
                                    historial.getVeterinarioId());

                    return convertirResponse(
                            historial,
                            mascota,
                            veterinario);

                })
                .toList();
    }

    @Override
    public HistorialResponse buscarPorId(
            Integer id) {

        HistorialClinico historial =
                historialRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Historial no encontrado"));

        MascotaResponse mascota =
                mascotaFeignClient.buscarPorId(
                        historial.getMascotaId());

        UsuarioResponse veterinario =
                usuarioFeignClient.buscarPorId(
                        historial.getVeterinarioId());

        return convertirResponse(
                historial,
                mascota,
                veterinario);
    }

    @Override
    public List<HistorialResponse>
            buscarPorMascota(
                    Integer mascotaId) {

        return historialRepository
                .findByMascotaId(
                        mascotaId)
                .stream()
                .map(historial -> {

                    MascotaResponse mascota =
                            mascotaFeignClient.buscarPorId(
                                    historial.getMascotaId());

                    UsuarioResponse veterinario =
                            usuarioFeignClient.buscarPorId(
                                    historial.getVeterinarioId());

                    return convertirResponse(
                            historial,
                            mascota,
                            veterinario);

                })
                .toList();
    }

    private void validarDatosHistorial(
            MascotaResponse mascota,
            UsuarioResponse veterinario) {

        if (!mascota.getEstado()) {

            throw new BusinessException(
                    "La mascota está inactiva");
        }

        if (!veterinario.getEstado()) {

            throw new BusinessException(
                    "El veterinario está inactivo");
        }

        if (!"VETERINARIO".equals(
                veterinario.getRol())) {

            throw new BusinessException(
                    "El usuario indicado no es veterinario");
        }
    }

    private HistorialResponse convertirResponse(
            HistorialClinico historial,
            MascotaResponse mascota,
            UsuarioResponse veterinario) {

        return HistorialResponse.builder()
                .id(
                        historial.getId())

                .citaId(
                        historial.getCitaId())

                .mascotaId(
                        mascota.getId())

                .nombreMascota(
                        mascota.getNombre())

                .veterinarioId(
                        veterinario.getId())

                .nombreVeterinario(
                        veterinario.getNombre()
                        + " "
                        + veterinario.getApellido())

                .diagnostico(
                        historial.getDiagnostico())

                .tratamiento(
                        historial.getTratamiento())

                .observaciones(
                        historial.getObservaciones())

                .fechaRegistro(
                        historial.getFechaRegistro())

                .build();
    }
}
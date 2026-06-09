package com.cita.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cita.service.dto.CitaRequest;
import com.cita.service.dto.CitaResponse;
import com.cita.service.dto.MascotaResponse;
import com.cita.service.dto.UsuarioResponse;
import com.cita.service.entity.Cita;
import com.cita.service.entity.enums.EstadoCita;
import com.cita.service.service.BusinessException;
import com.cita.service.feign.MascotaFeignClient;
import com.cita.service.feign.UsuarioFeignClient;
import com.cita.service.repository.CitaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CitaServiceImpl
        implements CitaService {

    private final CitaRepository citaRepository;

    private final UsuarioFeignClient usuarioFeignClient;

    private final MascotaFeignClient mascotaFeignClient;

    @Override
    public CitaResponse registrar(
            CitaRequest request) {

        UsuarioResponse cliente =
                usuarioFeignClient.buscarPorId(
                        request.getClienteId());

        UsuarioResponse veterinario =
                usuarioFeignClient.buscarPorId(
                        request.getVeterinarioId());

        MascotaResponse mascota =
                mascotaFeignClient.buscarPorId(
                        request.getMascotaId());

        validarDatosCita(
                cliente,
                veterinario,
                mascota,
                request.getClienteId());

        boolean horarioOcupado =
                citaRepository
                        .existsByVeterinarioIdAndFechaAndHora(
                                request.getVeterinarioId(),
                                request.getFecha(),
                                request.getHora());

        if (horarioOcupado) {

            throw new BusinessException(
                    "El veterinario ya tiene una cita en ese horario");
        }

        Cita cita = Cita.builder()
                .fecha(request.getFecha())
                .hora(request.getHora())
                .motivo(request.getMotivo())
                .clienteId(request.getClienteId())
                .mascotaId(request.getMascotaId())
                .veterinarioId(request.getVeterinarioId())
                .estado(EstadoCita.PENDIENTE)
                .build();

        Cita guardada =
                citaRepository.save(cita);

        return convertirResponse(
                guardada,
                cliente,
                veterinario,
                mascota);
    }

    @Override
    public List<CitaResponse> listar() {

        return citaRepository.findAll()
                .stream()
                .map(cita -> {

                    UsuarioResponse cliente =
                            usuarioFeignClient.buscarPorId(
                                    cita.getClienteId());

                    UsuarioResponse veterinario =
                            usuarioFeignClient.buscarPorId(
                                    cita.getVeterinarioId());

                    MascotaResponse mascota =
                            mascotaFeignClient.buscarPorId(
                                    cita.getMascotaId());

                    return convertirResponse(
                            cita,
                            cliente,
                            veterinario,
                            mascota);
                })
                .toList();
    }

    @Override
    public CitaResponse buscarPorId(
            Integer id) {

        Cita cita =
                citaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Cita no encontrada"));

        UsuarioResponse cliente =
                usuarioFeignClient.buscarPorId(
                        cita.getClienteId());

        UsuarioResponse veterinario =
                usuarioFeignClient.buscarPorId(
                        cita.getVeterinarioId());

        MascotaResponse mascota =
                mascotaFeignClient.buscarPorId(
                        cita.getMascotaId());

        return convertirResponse(
                cita,
                cliente,
                veterinario,
                mascota);
    }

    @Override
    public CitaResponse actualizar(
            Integer id,
            CitaRequest request) {

        Cita cita =
                citaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Cita no encontrada"));

        UsuarioResponse cliente =
                usuarioFeignClient.buscarPorId(
                        request.getClienteId());

        UsuarioResponse veterinario =
                usuarioFeignClient.buscarPorId(
                        request.getVeterinarioId());

        MascotaResponse mascota =
                mascotaFeignClient.buscarPorId(
                        request.getMascotaId());

        validarDatosCita(
                cliente,
                veterinario,
                mascota,
                request.getClienteId());

        boolean horarioOcupado =
                citaRepository
                        .existsByVeterinarioIdAndFechaAndHoraAndIdNot(
                                request.getVeterinarioId(),
                                request.getFecha(),
                                request.getHora(),
                                id);

        if (horarioOcupado) {

            throw new BusinessException(
                    "El veterinario ya tiene una cita en ese horario");
        }

        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMotivo(request.getMotivo());
        cita.setClienteId(request.getClienteId());
        cita.setMascotaId(request.getMascotaId());
        cita.setVeterinarioId(request.getVeterinarioId());

        Cita actualizada =
                citaRepository.save(cita);

        return convertirResponse(
                actualizada,
                cliente,
                veterinario,
                mascota);
    }

    @Override
    public void eliminar(
            Integer id) {

        Cita cita =
                citaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Cita no encontrada"));

        if (cita.getEstado()
                == EstadoCita.ATENDIDA) {

            throw new BusinessException(
                    "No se puede cancelar una cita atendida");
        }

        cita.setEstado(
                EstadoCita.CANCELADA);

        citaRepository.save(cita);
    }

    @Override
    public void confirmar(
            Integer id) {

        Cita cita =
                citaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Cita no encontrada"));

        if (cita.getEstado()
                != EstadoCita.PENDIENTE) {

            throw new BusinessException(
                    "Solo se pueden confirmar citas pendientes");
        }

        cita.setEstado(
                EstadoCita.CONFIRMADA);

        citaRepository.save(cita);
    }

    @Override
    public void atender(
            Integer id) {

        Cita cita =
                citaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Cita no encontrada"));

        if (cita.getEstado()
                != EstadoCita.CONFIRMADA) {

            throw new BusinessException(
                    "Solo se pueden atender citas confirmadas");
        }

        cita.setEstado(
                EstadoCita.ATENDIDA);

        citaRepository.save(cita);
    }

    @Override
    public List<CitaResponse> buscarPorEstado(
            EstadoCita estado) {

        return citaRepository
                .findByEstado(estado)
                .stream()
                .map(cita -> {

                    UsuarioResponse cliente =
                            usuarioFeignClient.buscarPorId(
                                    cita.getClienteId());

                    UsuarioResponse veterinario =
                            usuarioFeignClient.buscarPorId(
                                    cita.getVeterinarioId());

                    MascotaResponse mascota =
                            mascotaFeignClient.buscarPorId(
                                    cita.getMascotaId());

                    return convertirResponse(
                            cita,
                            cliente,
                            veterinario,
                            mascota);
                })
                .toList();
    }

    private void validarDatosCita(
            UsuarioResponse cliente,
            UsuarioResponse veterinario,
            MascotaResponse mascota,
            Integer clienteId) {

        if (!cliente.getEstado()) {

            throw new BusinessException(
                    "El cliente está inactivo");
        }

        if (!veterinario.getEstado()) {

            throw new BusinessException(
                    "El veterinario está inactivo");
        }

        if (!mascota.getEstado()) {

            throw new BusinessException(
                    "La mascota está inactiva");
        }

        if (!"VETERINARIO".equals(
                veterinario.getRol())) {

            throw new BusinessException(
                    "El usuario indicado no es veterinario");
        }

        if (!mascota.getClienteId()
                .equals(clienteId)) {

            throw new BusinessException(
                    "La mascota no pertenece al cliente");
        }
    }

    private CitaResponse convertirResponse(
            Cita cita,
            UsuarioResponse cliente,
            UsuarioResponse veterinario,
            MascotaResponse mascota) {

        return CitaResponse.builder()
                .id(cita.getId())
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .motivo(cita.getMotivo())
                .estado(cita.getEstado())

                .clienteId(cliente.getId())
                .nombreCliente(
                        cliente.getNombre()
                        + " "
                        + cliente.getApellido())

                .mascotaId(mascota.getId())
                .nombreMascota(
                        mascota.getNombre())

                .veterinarioId(veterinario.getId())
                .nombreVeterinario(
                        veterinario.getNombre()
                        + " "
                        + veterinario.getApellido())

                .build();
    }
}
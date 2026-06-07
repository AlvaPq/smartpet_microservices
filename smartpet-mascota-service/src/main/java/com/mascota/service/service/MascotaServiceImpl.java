package com.mascota.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mascota.service.dto.MascotaRequest;
import com.mascota.service.dto.MascotaResponse;
import com.mascota.service.dto.UsuarioResponse;
import com.mascota.service.entity.Mascota;
import com.mascota.service.exception.BusinessException;
import com.mascota.service.feign.UsuarioFeignClient;
import com.mascota.service.repository.MascotaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MascotaServiceImpl
        implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioFeignClient usuarioFeignClient;
    @Override
    public MascotaResponse registrar(
            MascotaRequest request) {
    	UsuarioResponse cliente =
    	        usuarioFeignClient.buscarPorId(
    	                request.getClienteId());

        Mascota mascota = Mascota.builder()
                .nombre(request.getNombre())
                .especie(request.getEspecie())
                .raza(request.getRaza())
                .sexo(request.getSexo())
                .fechaNacimiento(
                        request.getFechaNacimiento())
                .peso(request.getPeso())
                .clienteId(
                        request.getClienteId())
                .estado(true)
                .build();

        Mascota guardada =
                mascotaRepository.save(
                        mascota);

        return convertirResponse(
                guardada, cliente);
    }

    
    
    @Override
    public List<MascotaResponse> listar() {

        return mascotaRepository.findByEstadoTrue()
                .stream()
                .map(mascota -> {

                    UsuarioResponse cliente =
                            usuarioFeignClient
                                    .buscarPorId(
                                            mascota.getClienteId());

                    return convertirResponse(
                            mascota,
                            cliente);
                })
                .collect(Collectors.toList());
    }

    @Override
    public MascotaResponse actualizar(
            Integer id,
            MascotaRequest request) {

        Mascota mascota =
                mascotaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Mascota no encontrada"));

        mascota.setNombre(
                request.getNombre());

        mascota.setEspecie(
                request.getEspecie());

        mascota.setRaza(
                request.getRaza());

        mascota.setSexo(
                request.getSexo());

        mascota.setFechaNacimiento(
                request.getFechaNacimiento());

        mascota.setPeso(
                request.getPeso());

        mascota.setClienteId(
                request.getClienteId());

        Mascota actualizada =
                mascotaRepository.save(
                        mascota);

        UsuarioResponse cliente =
                usuarioFeignClient.buscarPorId(
                        actualizada.getClienteId());

        return convertirResponse(
                actualizada,
                cliente);
    }

    @Override
    public void eliminar(
            Integer id) {

        Mascota mascota =
                mascotaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Mascota no encontrada"));

        mascota.setEstado(false);

        mascotaRepository.save(
                mascota);
    }

    private MascotaResponse convertirResponse(
            Mascota mascota, UsuarioResponse cliente) {

        return MascotaResponse.builder()
                .id(mascota.getId())
                .nombre(mascota.getNombre())
                .especie(mascota.getEspecie())
                .raza(mascota.getRaza())
                .sexo(mascota.getSexo())
                .fechaNacimiento(
                        mascota.getFechaNacimiento())
                .peso(mascota.getPeso())
                .estado(mascota.getEstado())
                .clienteId(
                        mascota.getClienteId())
                .nombreCliente(
                        cliente.getNombre()
                        + " "
                        + cliente.getApellido())
                .build();
    }



    @Override
    public MascotaResponse buscarPorId(
            Integer id) {

        Mascota mascota =
                mascotaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Mascota no encontrada"));

        UsuarioResponse cliente =
                usuarioFeignClient.buscarPorId(
                        mascota.getClienteId());

        return convertirResponse(
                mascota,
                cliente);
    }
    
    @Override
    public List<MascotaResponse>
            buscarPorCliente(
                    Integer clienteId) {

        return mascotaRepository
                .findByClienteIdAndEstadoTrue(
                        clienteId)
                .stream()
                .map(mascota -> {

                    UsuarioResponse cliente =
                            usuarioFeignClient
                                    .buscarPorId(
                                            mascota.getClienteId());

                    return convertirResponse(
                            mascota,
                            cliente);

                })
                .toList();
    }
    
    @Override
    public void activar(
            Integer id) {

        Mascota mascota =
                mascotaRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Mascota no encontrada"));

        mascota.setEstado(true);

        mascotaRepository.save(
                mascota);
    }
    
}
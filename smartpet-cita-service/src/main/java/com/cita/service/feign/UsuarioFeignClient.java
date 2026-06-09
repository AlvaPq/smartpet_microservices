package com.cita.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cita.service.dto.UsuarioResponse;

@FeignClient(name = "AUTH-SERVICE")
public interface UsuarioFeignClient {

    @GetMapping("/auth/usuarios/{id}")
    UsuarioResponse buscarPorId(
            @PathVariable Integer id);

}
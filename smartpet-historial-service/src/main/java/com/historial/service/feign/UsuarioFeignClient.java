package com.historial.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.historial.service.dto.UsuarioResponse;

@FeignClient(name = "AUTH-SERVICE")
public interface UsuarioFeignClient {

    @GetMapping("/auth/usuarios/{id}")
    UsuarioResponse buscarPorId(
            @PathVariable Integer id);
}
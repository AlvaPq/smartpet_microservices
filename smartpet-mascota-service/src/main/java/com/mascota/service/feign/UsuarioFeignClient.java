package com.mascota.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "AUTH-SERVICE")
public interface UsuarioFeignClient {

    @GetMapping("/auth/usuarios/{id}")
    com.mascota.service.dto.UsuarioResponse buscarPorId(
            @PathVariable Integer id);

}
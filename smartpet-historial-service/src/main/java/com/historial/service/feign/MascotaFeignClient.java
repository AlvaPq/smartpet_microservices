package com.historial.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.historial.service.dto.MascotaResponse;

@FeignClient(name = "MASCOTA-SERVICE")
public interface MascotaFeignClient {

    @GetMapping("/mascotas/{id}")
    MascotaResponse buscarPorId(
            @PathVariable Integer id);
}
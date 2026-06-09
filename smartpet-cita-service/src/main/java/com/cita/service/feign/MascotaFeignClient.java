package com.cita.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cita.service.dto.MascotaResponse;

@FeignClient(name = "MASCOTA-SERVICE")
public interface MascotaFeignClient {

    @GetMapping("/mascotas/{id}")
    MascotaResponse buscarPorId(
            @PathVariable Integer id);

}
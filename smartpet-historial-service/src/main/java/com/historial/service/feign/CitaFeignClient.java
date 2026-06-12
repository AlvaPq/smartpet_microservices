package com.historial.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.historial.service.dto.CitaResponse;

@FeignClient(name = "CITA-SERVICE")
public interface CitaFeignClient {

    @GetMapping("/citas/{id}")
    CitaResponse buscarPorId(
            @PathVariable Integer id);
}
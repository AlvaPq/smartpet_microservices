package com.historial.service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.historial.service.dto.HistorialRequest;
import com.historial.service.dto.HistorialResponse;
import com.historial.service.service.HistorialService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/historiales")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;

    @PostMapping
    public ResponseEntity<HistorialResponse>
            registrar(
                    @Valid
                    @RequestBody
                    HistorialRequest request) {

        return ResponseEntity.ok(
                historialService.registrar(
                        request));
    }

    @GetMapping
    public ResponseEntity<List<HistorialResponse>>
            listar() {

        return ResponseEntity.ok(
                historialService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialResponse>
            buscarPorId(
                    @PathVariable
                    Integer id) {

        return ResponseEntity.ok(
                historialService.buscarPorId(
                        id));
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<HistorialResponse>>
            buscarPorMascota(
                    @PathVariable
                    Integer mascotaId) {

        return ResponseEntity.ok(
                historialService.buscarPorMascota(
                        mascotaId));
    }
}
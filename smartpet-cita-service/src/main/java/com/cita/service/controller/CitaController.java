package com.cita.service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cita.service.dto.CitaRequest;
import com.cita.service.dto.CitaResponse;
import com.cita.service.entity.enums.EstadoCita;
import com.cita.service.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaResponse>
            registrar(
                    @Valid
                    @RequestBody
                    CitaRequest request) {

        return ResponseEntity.ok(
                citaService.registrar(
                        request));
    }

    @GetMapping
    public ResponseEntity<List<CitaResponse>>
            listar() {

        return ResponseEntity.ok(
                citaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse>
            buscarPorId(
                    @PathVariable
                    Integer id) {

        return ResponseEntity.ok(
                citaService.buscarPorId(
                        id));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaResponse>>
            buscarPorEstado(
                    @PathVariable
                    EstadoCita estado) {

        return ResponseEntity.ok(
                citaService.buscarPorEstado(
                        estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponse>
            actualizar(
                    @PathVariable
                    Integer id,

                    @Valid
                    @RequestBody
                    CitaRequest request) {

        return ResponseEntity.ok(
                citaService.actualizar(
                        id,
                        request));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void>
            confirmar(
                    @PathVariable
                    Integer id) {

        citaService.confirmar(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/{id}/atender")
    public ResponseEntity<Void>
            atender(
                    @PathVariable
                    Integer id) {

        citaService.atender(id);

        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
            eliminar(
                    @PathVariable
                    Integer id) {

        citaService.eliminar(id);

        return ResponseEntity.noContent()
                .build();
    }
}
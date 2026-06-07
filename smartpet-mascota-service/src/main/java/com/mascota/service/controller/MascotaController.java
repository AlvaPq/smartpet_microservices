package com.mascota.service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mascota.service.dto.MascotaRequest;
import com.mascota.service.dto.MascotaResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final com.mascota.service.service.MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponse>
            registrar(
                    @Valid
                    @RequestBody
                    MascotaRequest request) {

        return ResponseEntity.ok(
                mascotaService.registrar(
                        request));
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponse>>
            listar() {

        return ResponseEntity.ok(
                mascotaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse>
            buscarPorId(
                    @PathVariable
                    Integer id) {

        return ResponseEntity.ok(
                mascotaService.buscarPorId(
                        id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponse>
            actualizar(
                    @PathVariable
                    Integer id,

                    @Valid
                    @RequestBody
                    MascotaRequest request) {

        return ResponseEntity.ok(
                mascotaService.actualizar(
                        id,
                        request));
    }
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<MascotaResponse>>
            buscarPorCliente(
                    @PathVariable Integer clienteId){

        return ResponseEntity.ok(
                mascotaService.buscarPorCliente(
                        clienteId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
            eliminar(
                    @PathVariable
                    Integer id) {

        mascotaService.eliminar(id);

        return ResponseEntity.noContent()
                .build();
    }
    
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void>
            activar(
                    @PathVariable
                    Integer id) {

        mascotaService.activar(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
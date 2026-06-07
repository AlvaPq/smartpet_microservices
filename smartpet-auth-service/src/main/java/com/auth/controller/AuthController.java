package com.auth.controller;

import com.auth.dto.ApiResponse;
import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.RegisterRequest;
import com.auth.dto.UsuarioResponse;
import com.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(
            @Valid
            @RequestBody RegisterRequest request) {

        UsuarioResponse response =
                authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> health() {

        return ResponseEntity.ok(
                new ApiResponse(
                        "Auth Service funcionando correctamente"));
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse>
            login(
                    @RequestBody
                    LoginRequest request){

        return ResponseEntity.ok(
                authService.login(request));
    }
    
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponse>
            buscarPorId(
                    @PathVariable Integer id) {

        return ResponseEntity.ok(
                authService.buscarPorId(id));
    }
    @GetMapping("/test")
    public String test() {

        return "JWT OK";

    }

    
    @GetMapping("/admin/test")
    public String adminTest() {

        return "ADMIN OK";
    }
    @GetMapping("/veterinario/test")
    public String veterinarioTest() {

        return "VETERINARIO OK";
    }
    @GetMapping("/cliente/test")
    public String clienteTest() {

        return "CLIENTE OK";
    }
    
}
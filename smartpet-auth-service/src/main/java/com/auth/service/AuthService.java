package com.auth.service;

import java.util.List;

import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.RegisterRequest;
import com.auth.dto.UsuarioResponse;

public interface AuthService {

    UsuarioResponse register(
            RegisterRequest request);
    
    AuthResponse login(
            LoginRequest request);
    
    
    UsuarioResponse buscarPorId(
            Integer id);

    
    List<UsuarioResponse> listarVeterinarios();
}
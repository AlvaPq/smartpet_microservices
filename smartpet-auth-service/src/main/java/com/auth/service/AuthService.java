package com.auth.service;

import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.RegisterRequest;
import com.auth.dto.UsuarioResponse;

public interface AuthService {

    UsuarioResponse register(
            RegisterRequest request);
    
    AuthResponse login(
            LoginRequest request);

}
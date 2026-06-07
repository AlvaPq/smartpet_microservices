package com.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.RegisterRequest;
import com.auth.dto.UsuarioResponse;
import com.auth.entity.Rol;
import com.auth.entity.Usuario;
import com.auth.exception.BusinessException;
import com.auth.repository.RolRepository;
import com.auth.repository.UsuarioRepository;
import com.auth.security.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl
        implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
    @Override
    public UsuarioResponse register(
            RegisterRequest request) {

        if (usuarioRepository.existsByCorreo(
                request.getCorreo())) {

            throw new BusinessException(
                    "El correo ya se encuentra registrado");
        }

        Rol clienteRol = rolRepository
                .findByNombre("CLIENTE")
                .orElseThrow(() ->
                        new BusinessException(
                                "Rol CLIENTE no encontrado"));

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .correo(request.getCorreo())
                .dni(request.getDni())
                .telefono(request.getTelefono())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .estado(true)
                .rol(clienteRol)
                .build();

        Usuario guardado =
                usuarioRepository.save(usuario);

        return UsuarioResponse.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .apellido(guardado.getApellido())
                .correo(guardado.getCorreo())
                .dni(guardado.getDni())
                .telefono(guardado.getTelefono())
                .rol(guardado.getRol().getNombre())
                .estado(guardado.getEstado())
                .build();
    }

    @Override
    public AuthResponse login(
            LoginRequest request) {

        Usuario usuario =
                usuarioRepository
                        .findByCorreo(
                                request.getCorreo())
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Usuario no encontrado"));

        boolean passwordValido =
                passwordEncoder.matches(
                        request.getPassword(),
                        usuario.getPassword());

        if (!passwordValido) {

            throw new BusinessException(
                    "Credenciales inválidas");
        }

        String token =
                jwtService.generateToken(
                        usuario.getCorreo());

        UsuarioResponse usuarioResponse =
                UsuarioResponse.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .correo(usuario.getCorreo())
                        .dni(usuario.getDni())
                        .telefono(usuario.getTelefono())
                        .rol(usuario.getRol().getNombre())
                        .estado(usuario.getEstado())
                        .build();

        return AuthResponse.builder()
                .token(token)
                .usuario(usuarioResponse)
                .build();
    }
    
    
    @Override
    public UsuarioResponse buscarPorId(
            Integer id) {

        Usuario usuario =
                usuarioRepository.findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Usuario no encontrado"));

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .dni(usuario.getDni())
                .telefono(usuario.getTelefono())
                .rol(usuario.getRol().getNombre())
                .estado(usuario.getEstado())
                .build();
    }
    
}
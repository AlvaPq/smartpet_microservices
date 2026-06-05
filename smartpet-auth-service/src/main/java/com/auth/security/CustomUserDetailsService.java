package com.auth.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.auth.entity.Usuario;
import com.auth.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(
            String correo)
            throws UsernameNotFoundException {

        Usuario usuario =
                usuarioRepository
                        .findByCorreo(correo)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Usuario no encontrado"));

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getPassword())
                .roles(usuario.getRol().getNombre())
                .build();
    }
}
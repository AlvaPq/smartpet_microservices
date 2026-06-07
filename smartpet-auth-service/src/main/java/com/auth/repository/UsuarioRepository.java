package com.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

	boolean existsByCorreo(String correo);
	

	Optional<Usuario> findByCorreo(String correo);
	
	Optional<Usuario> findById(Integer id);

}
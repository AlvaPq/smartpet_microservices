package com.mascota.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mascota.service.entity.Mascota;

@Repository
public interface MascotaRepository
        extends JpaRepository<Mascota,Integer> {
	
	List<Mascota> findByClienteId(
	        Integer clienteId);

	List<Mascota> findByEstadoTrue();

	List<Mascota> findByClienteIdAndEstadoTrue(
	        Integer clienteId);
}
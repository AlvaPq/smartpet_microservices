package com.cita.service.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cita.service.entity.Cita;
import com.cita.service.entity.enums.EstadoCita;

public interface CitaRepository
        extends JpaRepository<Cita, Integer> {
	
	boolean existsByVeterinarioIdAndFechaAndHora(
	        Integer veterinarioId,
	        LocalDate fecha,
	        LocalTime hora);

	boolean existsByVeterinarioIdAndFechaAndHoraAndIdNot(Integer veterinarioId, LocalDate fecha, LocalTime hora,
			Integer id);
	
	List<Cita> findByEstado(
	        EstadoCita estado);
	
	

}
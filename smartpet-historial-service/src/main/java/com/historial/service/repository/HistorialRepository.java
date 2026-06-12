package com.historial.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historial.service.entity.HistorialClinico;

@Repository
public interface HistorialRepository
        extends JpaRepository<
                HistorialClinico,
                Integer> {

    List<HistorialClinico>
            findByMascotaId(
                    Integer mascotaId);
}
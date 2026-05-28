package com.kavila.inventario.pruebaCast.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kavila.inventario.pruebaCast.models.ViwHistorialMovimientos;

@Repository
public interface ViwHistorialRepository extends JpaRepository<ViwHistorialMovimientos, UUID> {
    List<ViwHistorialMovimientos> findByTipo(String tipo);

    List<ViwHistorialMovimientos> findAllByOrderByFechaHoraDesc();
}
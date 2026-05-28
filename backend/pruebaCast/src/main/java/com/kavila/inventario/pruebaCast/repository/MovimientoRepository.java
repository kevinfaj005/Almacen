package com.kavila.inventario.pruebaCast.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kavila.inventario.pruebaCast.models.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {
}
package com.kavila.inventario.pruebaCast.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kavila.inventario.pruebaCast.models.Producto;

@Repository
public interface InventarioRepository extends JpaRepository<Producto, UUID> {
    List<Producto> findByEstatusTrue();
}

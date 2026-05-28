package com.kavila.inventario.pruebaCast.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class RolUsuario {
    @Id
    @Column(name = "idRol", nullable = false)
    private UUID idRol;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
}

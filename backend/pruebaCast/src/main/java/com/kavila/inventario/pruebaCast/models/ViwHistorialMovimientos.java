package com.kavila.inventario.pruebaCast.models;

import java.util.UUID;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "view_historial_movimientos")
public class ViwHistorialMovimientos {
    @Id
    @Column(name = "idMovimiento")
    private UUID idMovimiento;

    @Column(name = "idProducto")
    private UUID idProducto;

    @Column(name = "nombreProducto")
    private String nombreProducto;

    @Column(name = "idUsuario")
    private UUID idUsuario;

    @Column(name = "nombreUsuario")
    private String nombreUsuario;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fechaHora")
    private String fechaHora;

    @Column(name = "observaciones")
    private String observaciones;
}
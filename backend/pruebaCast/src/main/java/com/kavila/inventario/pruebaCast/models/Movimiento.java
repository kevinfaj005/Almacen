package com.kavila.inventario.pruebaCast.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movimientos")
public class Movimiento {
    @Id
    @Column(name = "idMovimiento")
    private UUID idMovimiento;

    @Column(name = "idProducto")
    private UUID idProducto;

    @Column(name = "idUsuario")
    private UUID idUsuario;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fechaHora")
    private LocalDateTime fecha;

    @Column(name = "observaciones")
    private String observaciones;
}

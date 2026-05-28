package com.kavila.inventario.pruebaCast.models;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Immutable
@Table(name = "view_login")
public class ViwLogin {
    @Id
    @Column(name = "idUsuario")
    private UUID idUsuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @JsonIgnore
    @Column(name = "contrasena")
    private String contrasena;

    @JsonIgnore
    @Column(name = "estatus")
    private Boolean estatus;

    @Column(name = "nombreRol")
    private String nombreRol;
}

package com.kavila.inventario.pruebaCast.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kavila.inventario.pruebaCast.models.Usuario;

@Repository
public interface AuthUserRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByIdUsuario(UUID idUsuario);
}

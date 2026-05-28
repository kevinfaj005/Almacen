package com.kavila.inventario.pruebaCast.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kavila.inventario.pruebaCast.models.ViwLogin;

@Repository
public interface ViwLoginRepository extends JpaRepository<ViwLogin, UUID> {
    Optional<ViwLogin> findByCorreo(String correo);
}

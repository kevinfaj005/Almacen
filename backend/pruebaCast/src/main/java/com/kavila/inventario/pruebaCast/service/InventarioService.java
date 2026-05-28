package com.kavila.inventario.pruebaCast.service;

import java.util.UUID;
import com.kavila.inventario.pruebaCast.dto.ResponseDto;
import com.kavila.inventario.pruebaCast.models.Producto;

public interface InventarioService {
    ResponseDto getAll();

    ResponseDto getActivos();

    ResponseDto getById(UUID id);

    ResponseDto save(Producto producto, UUID idUsuario);

    ResponseDto update(Producto producto, UUID idUsuario);

    ResponseDto delete(UUID id);
}

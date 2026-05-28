package com.kavila.inventario.pruebaCast.service;

import java.util.UUID;
import com.kavila.inventario.pruebaCast.dto.ResponseDto;

public interface MovimientoService {
    ResponseDto registrarSalida(UUID idProducto, UUID idUsuario, Integer cantidad);

    ResponseDto registrarEntrada(UUID idProducto, UUID idUsuario, Integer cantidad);

    ResponseDto getHistorial(String tipo);
}
package com.kavila.inventario.pruebaCast.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kavila.inventario.pruebaCast.dto.ResponseDto;
import com.kavila.inventario.pruebaCast.models.Movimiento;
import com.kavila.inventario.pruebaCast.models.Producto;
import com.kavila.inventario.pruebaCast.repository.InventarioRepository;
import com.kavila.inventario.pruebaCast.repository.MovimientoRepository;
import com.kavila.inventario.pruebaCast.repository.ViwHistorialRepository;
import com.kavila.inventario.pruebaCast.service.MovimientoService;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ViwHistorialRepository viwHistorialRepository;

    @Override
    @Transactional
    public ResponseDto registrarSalida(UUID idProducto, UUID idUsuario, Integer cantidad) {
        try {
            Producto producto = inventarioRepository.findById(idProducto).orElse(null);

            if (producto == null || !producto.getEstatus()) {
                return ResponseDto.builder().success(false).message("Producto no disponible o inactivo").build();
            }

            if (cantidad > producto.getCantidad()) {
                return ResponseDto.builder().success(false)
                        .message("Error: No hay suficiente stock. Disponible: " + producto.getCantidad()).build();
            }

            producto.setCantidad(producto.getCantidad() - cantidad);
            inventarioRepository.save(producto);
            registrar(idProducto, idUsuario, cantidad, "SALIDA");

            return ResponseDto.builder().success(true).message("Salida registrada con éxito").build();
        } catch (Exception e) {
            return ResponseDto.builder().success(false).message("Error: " + e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public ResponseDto registrarEntrada(UUID idProducto, UUID idUsuario, Integer cantidad) {
        try {
            Producto producto = inventarioRepository.findById(idProducto).orElse(null);
            if (producto == null)
                return ResponseDto.builder().success(false).message("Producto no encontrado").build();

            producto.setCantidad(producto.getCantidad() + cantidad);
            inventarioRepository.save(producto);

            registrar(idProducto, idUsuario, cantidad, "ENTRADA");

            return ResponseDto.builder().success(true).message("Entrada registrada con éxito").build();
        } catch (Exception e) {
            return ResponseDto.builder().success(false).message("Error: " + e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto getHistorial(String tipo) {
        try {
            Object data;
            if (tipo != null && !tipo.isEmpty()) {
                data = viwHistorialRepository.findByTipo(tipo);
            } else {
                data = viwHistorialRepository.findAllByOrderByFechaHoraDesc();
            }
            return ResponseDto.builder()
                    .success(true)
                    .message("Historial obtenido")
                    .data(data)
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder().success(false).message("Error al obtener historial").build();
        }
    }

    private void registrar(UUID idProducto, UUID idUsuario, Integer cantidad, String tipo) {
        Movimiento mov = Movimiento.builder()
                .idMovimiento(UUID.randomUUID())
                .idProducto(idProducto)
                .idUsuario(idUsuario)
                .cantidad(cantidad)
                .tipo(tipo)
                .fecha(LocalDateTime.now())
                .build();
        movimientoRepository.save(mov);
    }
}
package com.kavila.inventario.pruebaCast.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kavila.inventario.pruebaCast.dto.ResponseDto;
import com.kavila.inventario.pruebaCast.models.Movimiento;
import com.kavila.inventario.pruebaCast.models.Producto;
import com.kavila.inventario.pruebaCast.repository.MovimientoRepository;
import com.kavila.inventario.pruebaCast.repository.InventarioRepository;
import com.kavila.inventario.pruebaCast.service.InventarioService;

@Service
public class InventarioServiceImpl implements InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Override
    public ResponseDto getAll() {
        try {
            List<Producto> inventarios = inventarioRepository.findAll();
            return ResponseDto.builder()
                    .success(true)
                    .message("Inventarios obtenidos exitosamente")
                    .data(inventarios)
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @Override
    public ResponseDto getActivos() {
        try {
            List<Producto> activos = inventarioRepository.findByEstatusTrue();
            return ResponseDto.builder()
                    .success(true)
                    .message("Productos activos obtenidos")
                    .data(activos)
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error al obtener productos activos: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto getById(UUID id) {
        try {
            Optional<Producto> producto = inventarioRepository.findById(id);
            if (producto.isPresent()) {
                return ResponseDto.builder()
                        .success(true)
                        .message("Producto encontrado")
                        .data(producto.get())
                        .build();
            }
            return ResponseDto.builder()
                    .success(false)
                    .message("Producto no encontrado")
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error al buscar producto: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional
    public ResponseDto save(Producto producto, UUID idUsuario) {
        try {
            if (producto.getIdProducto() == null) {
                producto.setIdProducto(UUID.randomUUID());
            }
            producto.setEstatus(true);
            Producto nuevo = inventarioRepository.save(producto);

            if (nuevo.getCantidad() > 0) {
                registrarMovimiento(nuevo.getIdProducto(), idUsuario, nuevo.getCantidad(), "entrada",
                        "Registro inicial de producto");
            }

            return ResponseDto.builder()
                    .success(true)
                    .message("Producto creado exitosamente")
                    .data(nuevo)
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error al crear producto: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional
    public ResponseDto update(Producto producto, UUID idUsuario) {
        try {
            Optional<Producto> existenteOpt = inventarioRepository.findById(producto.getIdProducto());
            if (existenteOpt.isEmpty()) {
                return ResponseDto.builder()
                        .success(false)
                        .message("No se puede actualizar, el producto no existe")
                        .build();
            }

            Producto anterior = existenteOpt.get();
            int diferencia = producto.getCantidad() - anterior.getCantidad();

            Producto actualizado = inventarioRepository.save(producto);

            if (diferencia > 0) {
                registrarMovimiento(actualizado.getIdProducto(), idUsuario, diferencia, "entrada",
                        "Restock automático por actualización");
            } else if (diferencia < 0) {
                registrarMovimiento(actualizado.getIdProducto(), idUsuario, Math.abs(diferencia), "salida",
                        "Ajuste automático de inventario");
            }

            return ResponseDto.builder()
                    .success(true)
                    .message("Producto actualizado exitosamente")
                    .data(actualizado)
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error al actualizar producto: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto delete(UUID id) {
        try {
            Optional<Producto> productoOpt = inventarioRepository.findById(id);
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                producto.setEstatus(false);
                inventarioRepository.save(producto);
                return ResponseDto.builder()
                        .success(true)
                        .message("Producto desactivado exitosamente")
                        .build();
            }
            return ResponseDto.builder()
                    .success(false)
                    .message("Producto no encontrado")
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error al eliminar producto: " + e.getMessage())
                    .build();
        }
    }

    private void registrarMovimiento(UUID idProducto, UUID idUsuario, int cantidad, String tipo, String obs) {
        Movimiento mov = Movimiento.builder()
                .idMovimiento(UUID.randomUUID())
                .idProducto(idProducto)
                .idUsuario(idUsuario)
                .tipo(tipo)
                .cantidad(cantidad)
                .fecha(LocalDateTime.now())
                .observaciones(obs)
                .build();
        movimientoRepository.save(mov);
    }
}

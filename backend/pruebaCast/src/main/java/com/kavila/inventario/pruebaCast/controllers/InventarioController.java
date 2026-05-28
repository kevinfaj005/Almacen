package com.kavila.inventario.pruebaCast.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kavila.inventario.pruebaCast.dto.ResponseDto;
import com.kavila.inventario.pruebaCast.models.Producto;
import com.kavila.inventario.pruebaCast.service.InventarioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseDto getAll() {
        try {
            return inventarioService.getAll();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/activos")
    public ResponseDto getActivos() {
        try {
            return inventarioService.getActivos();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseDto getById(@PathVariable UUID id) {
        try {
            return inventarioService.getById(id);
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseDto save(@RequestBody Producto producto, @RequestHeader("X-User-Id") UUID idUsuario) {
        try {
            return inventarioService.save(producto, idUsuario);
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .build();
        }
    }

    @PutMapping
    public ResponseDto update(@RequestBody Producto producto, @RequestHeader("X-User-Id") UUID idUsuario) {
        try {
            if (producto.getIdProducto() == null) {
                return ResponseDto.builder()
                        .success(false)
                        .message("El ID del producto es obligatorio para actualizar")
                        .build();
            }
            return inventarioService.update(producto, idUsuario);
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable UUID id) {
        try {
            return inventarioService.delete(id);
        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .build();
        }
    }

}

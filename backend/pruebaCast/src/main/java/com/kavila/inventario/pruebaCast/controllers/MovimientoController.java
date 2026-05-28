package com.kavila.inventario.pruebaCast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kavila.inventario.pruebaCast.dto.ResponseDto;
import com.kavila.inventario.pruebaCast.models.Movimiento;
import com.kavila.inventario.pruebaCast.service.MovimientoService;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping("/salida")
    public ResponseDto salida(@RequestBody Movimiento req) {
        return movimientoService.registrarSalida(req.getIdProducto(), req.getIdUsuario(), req.getCantidad());
    }

    @PostMapping("/entrada")
    public ResponseDto entrada(@RequestBody Movimiento req) {
        return movimientoService.registrarEntrada(req.getIdProducto(), req.getIdUsuario(), req.getCantidad());
    }

    @GetMapping("/historial")
    public ResponseDto getHistorial(@RequestParam(required = false) String tipo) {
        return movimientoService.getHistorial(tipo);
    }
}
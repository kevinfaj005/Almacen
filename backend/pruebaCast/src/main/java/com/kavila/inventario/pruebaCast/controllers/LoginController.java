package com.kavila.inventario.pruebaCast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.kavila.inventario.pruebaCast.dto.LoginUserDto;
import com.kavila.inventario.pruebaCast.dto.ResponseDto;
import com.kavila.inventario.pruebaCast.service.AuthService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping("/prueba")
    public String getMethodName() {
        return new String("Servicio funcionando padre ");
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginUserDto loginUserDto) {
        try {
            return authService.login(loginUserDto);

        } catch (Exception e) {
            return ResponseDto.builder()
                    .success(false)
                    .message("Error del servidor: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

}

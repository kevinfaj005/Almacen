package com.kavila.inventario.pruebaCast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kavila.inventario.pruebaCast.dto.LoginUserDto;
import com.kavila.inventario.pruebaCast.dto.ResponseDto;
import com.kavila.inventario.pruebaCast.models.Usuario;
import com.kavila.inventario.pruebaCast.models.ViwLogin;
import com.kavila.inventario.pruebaCast.repository.AuthUserRepository;
import com.kavila.inventario.pruebaCast.repository.ViwLoginRepository;
import com.kavila.inventario.pruebaCast.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private ViwLoginRepository viwLoginRepository;

    public ResponseDto login(LoginUserDto loginUserDto) {
        Usuario autUsuario = authUserRepository.findByCorreo(loginUserDto.getCorreo()).orElse(null);

        if (autUsuario != null && autUsuario.getContrasena().equals(loginUserDto.getContrasena())) {
            ViwLogin viwLogin = viwLoginRepository.findByCorreo(loginUserDto.getCorreo()).orElse(null);

            if (viwLogin == null) {
                return ResponseDto.builder()
                        .success(false)
                        .message("Error del servidor, no se encontraron los detalles del usuario")
                        .data(null)
                        .build();
            }
            return ResponseDto.builder()
                    .success(true)
                    .message("Credenciales correctas, login exitoso")
                    .data(viwLogin)
                    .build();
        } else {
            return ResponseDto.builder()
                    .success(false)
                    .message("Credenciales incorrectas")
                    .data(null)
                    .build();
        }
    }
}

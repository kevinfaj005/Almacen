package com.kavila.inventario.pruebaCast.service;

import com.kavila.inventario.pruebaCast.dto.LoginUserDto;
import com.kavila.inventario.pruebaCast.dto.ResponseDto;

public interface AuthService {
    ResponseDto login(LoginUserDto loginUserDto);
}

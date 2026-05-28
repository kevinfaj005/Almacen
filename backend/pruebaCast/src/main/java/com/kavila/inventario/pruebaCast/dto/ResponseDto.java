package com.kavila.inventario.pruebaCast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto {
    Boolean success;
    String message;
    String exception;
    Object data;
}
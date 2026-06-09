package com.cita.service.service;

public class BusinessException
        extends RuntimeException {

    public BusinessException(
            String mensaje) {

        super(mensaje);
    }
}
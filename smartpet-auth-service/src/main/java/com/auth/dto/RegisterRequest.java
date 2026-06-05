package com.auth.dto;
import lombok.Data;

@Data
public class RegisterRequest {

    private String nombre;

    private String apellido;

    private String correo;

    private String password;

    private String rol;
}
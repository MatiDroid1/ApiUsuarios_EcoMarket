package com.usuarios.duoc.cl.usuarios.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long usuarioId;
    private String nombre;
    private String email;
    private Long rolId;
    private String telefono;
    private String direccion;
    private String rut;
    private String tipoUsuario;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    // getters y setters
    // ...
}

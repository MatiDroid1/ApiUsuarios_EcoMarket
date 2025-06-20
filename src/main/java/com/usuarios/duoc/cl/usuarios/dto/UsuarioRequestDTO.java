
package com.usuarios.duoc.cl.usuarios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email es obligatorio")
    private String email;

    @NotBlank(message = "Password es obligatorio")
    private String password;

    @NotNull(message = "RolId es obligatorio")
    private Long rolId;

    @NotBlank(message = "Teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "Dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "Rut es obligatorio")
    private String rut;

    @NotBlank(message = "Tipo usuario es obligatorio")
    private String tipoUsuario;

    private Boolean activo = true;

}

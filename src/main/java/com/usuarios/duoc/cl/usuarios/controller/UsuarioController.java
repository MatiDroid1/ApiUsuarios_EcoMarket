package com.usuarios.duoc.cl.usuarios.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuarios.duoc.cl.usuarios.dto.UsuarioDTO;
import com.usuarios.duoc.cl.usuarios.dto.UsuarioRequestDTO;
import com.usuarios.duoc.cl.usuarios.dto.UsuarioResponseDTO;
import com.usuarios.duoc.cl.usuarios.model.Usuario;
import com.usuarios.duoc.cl.usuarios.services.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuario por rut
    @GetMapping("/rut/{rut}")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rut) {
        Usuario usuario = usuarioService.buscarPorRut(rut);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Buscar usuario por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarXid(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    // Crear usuario nuevo
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.buscarPorRut(usuario.getRut());
            if (usuarioExistente != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
            }
            usuarioService.guardar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear usuario");
        }
    }

    // Actualizar usuario por rut
    @PutMapping("/{rut}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String rut, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.buscarPorRut(rut);
            if (usuarioExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            // Actualiza solo campos editables, puedes agregar más si quieres
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setPasswordHash(usuario.getPasswordHash());
            usuarioExistente.setRolId(usuario.getRolId());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setTipoUsuario(usuario.getTipoUsuario());
            usuarioExistente.setActivo(usuario.getActivo());

            usuarioService.guardar(usuarioExistente);
            return ResponseEntity.ok("Usuario actualizado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario");
        }
    }

    // Login básico
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        Usuario usuario = usuarioService.buscarPorRut(loginRequest.getRut());
        if (usuario == null || !usuario.getPasswordHash().equals(loginRequest.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Rut o contraseña inválidos");
        }
        return ResponseEntity.ok("Login exitoso");
    }
}
package com.usuarios.duoc.cl.usuarios.controller;

import java.util.List;

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

import com.usuarios.duoc.cl.usuarios.model.Usuario;
import com.usuarios.duoc.cl.usuarios.services.UsuarioService;



@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        if(usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios registrados");
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }
    
    @GetMapping("/{rut}")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rut){
        Usuario usuario = usuarioService.buscarPorRut(rut);
        if(usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } else {
            return ResponseEntity.ok(usuario);
        }
    }

    @PostMapping
   public ResponseEntity<?> crearUsuario(@RequestBody Usuario usu){
        try{
            Usuario usuarioExistente = usuarioService.buscarPorRut(usu.getRut());
            if (usuarioExistente != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
            }
            usuarioService.guardar(usu);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear usuario");
        }
   }

    @PutMapping("/{rut}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String rut, @RequestBody Usuario usu) {
        try{
            Usuario usuarioExistente = usuarioService.buscarPorRut(rut);
            if (usuarioExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            usuarioExistente.setNombre(usu.getNombre());
            usuarioExistente.setEmail(usu.getEmail());
            usuarioExistente.setPasswordHash(usu.getPasswordHash());
            usuarioExistente.setRolId(usu.getRolId());
            usuarioExistente.setTelefono(usu.getTelefono());
            usuarioExistente.setDireccion(usu.getDireccion());
            usuarioExistente.setTipoUsuario(usu.getTipoUsuario());
            usuarioExistente.setActivo(usu.getActivo());
            usuarioService.guardar(usuarioExistente);
            return ResponseEntity.ok("Usuario actualizado con exito");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario");
        }
    }
}
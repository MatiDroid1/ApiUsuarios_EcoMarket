package com.usuarios.duoc.cl.usuarios.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usuarios.duoc.cl.usuarios.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    Optional<Usuario> findByRut(String rut);
    void deleteByRut(String rut);
}

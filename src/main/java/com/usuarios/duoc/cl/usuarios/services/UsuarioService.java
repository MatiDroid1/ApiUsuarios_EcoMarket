package com.usuarios.duoc.cl.usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuarios.duoc.cl.usuarios.model.Usuario;
import com.usuarios.duoc.cl.usuarios.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository ur;

    public List<Usuario> listarTodos() {
        return ur.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        return ur.save(usuario);
    }

    public Usuario buscarPorRut(String rut) {
        return ur.findByRut(rut).orElse(null);
    }

    public Usuario buscarXid(Long id){
        return ur.findById(id).orElse(null);
    }



    public void eliminarPorRut(String rut) {
        ur.deleteByRut(rut);
    }
}
    
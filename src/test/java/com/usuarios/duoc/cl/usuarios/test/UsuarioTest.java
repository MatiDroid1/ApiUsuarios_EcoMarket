package com.usuarios.duoc.cl.usuarios.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usuarios.duoc.cl.usuarios.model.Usuario;
import com.usuarios.duoc.cl.usuarios.repository.UsuarioRepository;
import com.usuarios.duoc.cl.usuarios.services.UsuarioService;

public class UsuarioTest {
    @Mock
    private UsuarioRepository uRepository;

    @InjectMocks
    private UsuarioService uService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarPorRut() {
        // Arrange
        String rut = "12345678-9";
        Usuario mockUsuario = new Usuario();
        mockUsuario.setRut(rut);
        mockUsuario.setNombre("Juanito");

        when(uRepository.findByRut(rut)).thenReturn(Optional.of(mockUsuario));

        // Act
        Usuario resultado = uService.buscarPorRut(rut);

        // Assert
        assertNotNull(resultado);
        assertEquals("Juanito", resultado.getNombre());
        verify(uRepository, times(1)).findByRut(rut);

        // Debug opcional
        System.out.println("✔ Test pasó: " + resultado.getNombre());
    }
}

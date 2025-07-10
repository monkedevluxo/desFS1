package com.usuario.service.usuario_service;

import com.usuario.service.usuario_service.entidades.Usuario;
import com.usuario.service.usuario_service.repositorio.UsuarioRepository;
import com.usuario.service.usuario_service.servicio.UsuarioServicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServicioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServicio usuarioServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        Usuario u1 = new Usuario();
        u1.setId(1);
        u1.setNombre("Juan");

        Usuario u2 = new Usuario();
        u2.setId(2);
        u2.setNombre("Ana");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> resultado = usuarioServicio.getAll();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testGetUsuarioById_Existente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Pedro");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioServicio.getUsuarioById(1);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    public void testGetUsuarioById_NoExistente() {
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        Usuario resultado = usuarioServicio.getUsuarioById(999);

        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById(999);
    }

    @Test
    public void testSave() {
        Usuario nuevo = new Usuario();
        nuevo.setNombre("Laura");

        Usuario guardado = new Usuario();
        guardado.setId(10);
        guardado.setNombre("Laura");

        when(usuarioRepository.save(nuevo)).thenReturn(guardado);

        Usuario resultado = usuarioServicio.save(nuevo);

        assertNotNull(resultado);
        assertEquals(10, resultado.getId());
        assertEquals("Laura", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(nuevo);
    }

    @Test
    public void testBorrarUsuario() {
        int id = 5;

        doNothing().when(usuarioRepository).deleteById(id);

        usuarioServicio.borrarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}

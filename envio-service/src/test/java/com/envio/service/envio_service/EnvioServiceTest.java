package com.envio.service.envio_service;

import com.envio.service.envio_service.entidades.Envio;
import com.envio.service.envio_service.repositorio.EnvioRepository;
import com.envio.service.envio_service.servicio.EnvioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Envio envio1 = new Envio();
        Envio envio2 = new Envio();
        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio1, envio2));

        List<Envio> resultado = envioService.getAll();

        assertEquals(2, resultado.size());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Envio envio = new Envio();
        envio.setId(1L);
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        Envio resultado = envioService.getById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(envioRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByEstado() {
        Envio envio1 = new Envio();
        envio1.setEstado("en camino");
        Envio envio2 = new Envio();
        envio2.setEstado("en camino");

        when(envioRepository.findByEstado("en camino")).thenReturn(Arrays.asList(envio1, envio2));

        List<Envio> resultado = envioService.getByEstado("en camino");

        assertEquals(2, resultado.size());
        assertEquals("en camino", resultado.get(0).getEstado());
    }

    @Test
    void testSave() {
        Envio envio = new Envio();
        envio.setEstado("pendiente");

        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.save(envio);

        assertEquals("pendiente", resultado.getEstado());
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void testActualizarEstado() {
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setEstado("pendiente");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);

        Envio actualizado = envioService.actualizarEstado(1L, "entregado");

        assertNotNull(actualizado);
        assertEquals("entregado", actualizado.getEstado());
        verify(envioRepository).save(envio);
    }

    @Test
    void testActualizarEstado_NotFound() {
        when(envioRepository.findById(99L)).thenReturn(Optional.empty());

        Envio actualizado = envioService.actualizarEstado(99L, "entregado");

        assertNull(actualizado);
    }

    @Test
    void testDeleteById() {
        Long id = 5L;
        envioService.deleteById(id);
        verify(envioRepository, times(1)).deleteById(id);
    }
}

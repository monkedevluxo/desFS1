package com.carrito.service.carrito_service;

import com.carrito.service.carrito_service.entidades.Carrito;
import com.carrito.service.carrito_service.repositorio.CarritoRepository;
import com.carrito.service.carrito_service.servicio.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarritoServiceTest {

    private CarritoRepository carritoRepository;
    private CarritoService carritoService;

    @BeforeEach
    public void setUp() {
        carritoRepository = mock(CarritoRepository.class);
        carritoService = new CarritoService(carritoRepository); 
    }

    @Test
    public void testGetAll() {
        Carrito c1 = new Carrito();
        Carrito c2 = new Carrito();
        when(carritoRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Carrito> result = carritoService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetCarritoById_Exists() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));

        Carrito result = carritoService.getCarritoById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetCarritoById_NotFound() {
        when(carritoRepository.findById(99)).thenReturn(Optional.empty());

        Carrito result = carritoService.getCarritoById(99);
        assertNull(result);
    }

    @Test
    public void testSave() {
        Carrito carrito = new Carrito();
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        Carrito result = carritoService.save(carrito);
        assertNotNull(result);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    public void testDeleteCarritoById() {
        carritoService.deleteCarritoById(1);
        verify(carritoRepository, times(1)).deleteById(1);
    }

    @Test
    public void testByUsuarioId() {
        Carrito c1 = new Carrito();
        Carrito c2 = new Carrito();
        when(carritoRepository.findByUsuarioId(10)).thenReturn(Arrays.asList(c1, c2));

        List<Carrito> result = carritoService.byUsuarioId(10);
        assertEquals(2, result.size());
    }
}

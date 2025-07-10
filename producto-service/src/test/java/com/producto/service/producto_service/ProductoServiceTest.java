package com.producto.service.producto_service;

import com.producto.service.producto_service.entidades.Producto;
import com.producto.service.producto_service.repositorio.ProductoRepository;
import com.producto.service.producto_service.servicio.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        producto1 = new Producto();
        producto1.setId(1);
        producto1.setMarca("Samsung");
        producto1.setModelo("A12");
        producto1.setUsuarioId(10);

        producto2 = new Producto();
        producto2.setId(2);
        producto2.setMarca("Apple");
        producto2.setModelo("iPhone 14");
        producto2.setUsuarioId(20);
    }

    @Test
    void testGetAll() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        List<Producto> productos = productoService.getAll();

        assertEquals(2, productos.size());
        assertEquals("Samsung", productos.get(0).getMarca());
    }

    @Test
    void testGetProductoById_Existente() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto1));

        Producto producto = productoService.getProductoById(1);

        assertNotNull(producto);
        assertEquals("Samsung", producto.getMarca());
    }

    @Test
    void testGetProductoById_NoExistente() {
        when(productoRepository.findById(999)).thenReturn(Optional.empty());

        Producto producto = productoService.getProductoById(999);

        assertNull(producto);
    }

    @Test
    void testSaveProducto() {
        when(productoRepository.save(producto1)).thenReturn(producto1);

        Producto guardado = productoService.save(producto1);

        assertNotNull(guardado);
        assertEquals("Samsung", guardado.getMarca());
    }

    @Test
    void testByUsuarioId() {
        when(productoRepository.findByUsuarioId(10)).thenReturn(List.of(producto1));

        List<Producto> productos = productoService.byUsuarioId(10);

        assertEquals(1, productos.size());
        assertEquals("Samsung", productos.get(0).getMarca());
    }

    @Test
    void testDeleteProductoById() {
        doNothing().when(productoRepository).deleteById(1);

        productoService.deleteProductoById(1);

        verify(productoRepository, times(1)).deleteById(1);
    }
}
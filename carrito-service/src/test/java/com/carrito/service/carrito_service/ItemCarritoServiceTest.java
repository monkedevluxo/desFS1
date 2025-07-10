package com.carrito.service.carrito_service;

import com.carrito.service.carrito_service.entidades.ItemCarrito;
import com.carrito.service.carrito_service.repositorio.ItemCarritoRepository;
import com.carrito.service.carrito_service.servicio.ItemCarritoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemCarritoServiceTest {
    private ItemCarritoRepository itemCarritoRepository;
    private ItemCarritoService itemCarritoService;

    @BeforeEach
    public void setUp() {
        itemCarritoRepository = mock(ItemCarritoRepository.class);
        itemCarritoService = new ItemCarritoService(itemCarritoRepository);
    }

    @Test
    public void testGetAll() {
        when(itemCarritoRepository.findAll()).thenReturn(Arrays.asList(new ItemCarrito(), new ItemCarrito()));
        List<ItemCarrito> result = itemCarritoService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetItemById_Exists() {
        ItemCarrito item = new ItemCarrito();
        item.setId(1);
        when(itemCarritoRepository.findById(1)).thenReturn(Optional.of(item));

        ItemCarrito result = itemCarritoService.getItemById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGetItemById_NotFound() {
        when(itemCarritoRepository.findById(999)).thenReturn(Optional.empty());
        ItemCarrito result = itemCarritoService.getItemById(999);
        assertNull(result);
    }

    @Test
    public void testSave() {
        ItemCarrito item = new ItemCarrito();
        when(itemCarritoRepository.save(item)).thenReturn(item);
        ItemCarrito result = itemCarritoService.save(item);
        assertNotNull(result);
    }

    @Test
    public void testDeleteById() {
        itemCarritoService.deleteItemById(1);
        verify(itemCarritoRepository, times(1)).deleteById(1);
    }

    @Test
    public void testByCarritoId() {
        when(itemCarritoRepository.findByCarritoId(5)).thenReturn(Arrays.asList(new ItemCarrito()));
        List<ItemCarrito> result = itemCarritoService.byCarritoId(5);
        assertEquals(1, result.size());
    }
}

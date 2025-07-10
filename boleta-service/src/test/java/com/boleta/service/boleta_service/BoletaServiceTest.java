package com.boleta.service.boleta_service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.boleta.service.boleta_service.entidades.Boleta;
import com.boleta.service.boleta_service.repositorio.BoletaRepositorio;
import com.boleta.service.boleta_service.servicio.BoletaService;
public class BoletaServiceTest {
    @Mock
    private BoletaRepositorio boletaRepository;

    @InjectMocks
    private BoletaService boletaService;   
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAll() {
        Boleta boleta1 = new Boleta(1L, null, null, null);
        Boleta boleta2 = new Boleta(2L, null, null, null);
        
        when(boletaRepository.findAll()).thenReturn(Arrays.asList(boleta1, boleta2));

        List<Boleta> result = boletaService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(boletaRepository, times(1)).findAll();
    }
    @Test
    public void testGetById_Exists() {
        Boleta b = new Boleta();
        b.setUsuarioId(1L);
        b.setFechaEmision(null);
        b.setTotal(null);

        when(boletaRepository.findById(1L)).thenReturn(Optional.of(b));
        Boleta result = boletaService.getById(1L);
        assertNotNull(result);
    }

    @Test
    public void testGetById_NotExists() {
        when(boletaRepository.findById(1L)).thenReturn(Optional.empty());
        Boleta result = boletaService.getById(1L);
        assertNull(result);
    }

    //Testear el insert de una boleta
    @Test
    public void testSave() {
        Boleta boleta = new Boleta(1L, null, null, null);
        when(boletaRepository.save(boleta)).thenReturn(boleta);

        Boleta result = boletaService.save(boleta);

        assertNotNull(result);
        assertEquals(boleta.getUsuarioId(), result.getUsuarioId());
        verify(boletaRepository, times(1)).save(boleta);
    }
    
    //Testear el delete de una boleta
    @Test
    public void testDeleteById() {
        Boleta boleta = new Boleta(1L, null, null, null);
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(boleta));

        boletaService.deleteById(1L);

        verify(boletaRepository, times(1)).deleteById(1L);
    }
}

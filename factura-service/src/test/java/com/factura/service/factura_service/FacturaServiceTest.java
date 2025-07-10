package com.factura.service.factura_service;

import com.factura.service.factura_service.entidades.Factura;
import com.factura.service.factura_service.repositorio.FacturaRepository;
import com.factura.service.factura_service.servicio.FacturaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaService facturaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        Factura f1 = new Factura(1L, "11.111.111-1", LocalDateTime.now(), new BigDecimal("10000"), new BigDecimal("1900"), new BigDecimal("11900"));
        Factura f2 = new Factura(2L, "22.222.222-2", LocalDateTime.now(), new BigDecimal("20000"), new BigDecimal("3800"), new BigDecimal("23800"));

        when(facturaRepository.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<Factura> resultado = facturaService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("22.222.222-2", resultado.get(1).getRutCliente());
    }

    @Test
    public void testGetById_Existe() {
        Factura f = new Factura(3L, "33.333.333-3", LocalDateTime.now(), new BigDecimal("5000"), new BigDecimal("950"), new BigDecimal("5950"));

        when(facturaRepository.findById(3L)).thenReturn(Optional.of(f));

        Factura resultado = facturaService.getById(3L);

        assertNotNull(resultado);
        assertEquals("33.333.333-3", resultado.getRutCliente());
    }

    @Test
    public void testGetById_NoExiste() {
        when(facturaRepository.findById(99L)).thenReturn(Optional.empty());

        Factura resultado = facturaService.getById(99L);

        assertNull(resultado);
    }

    @Test
    public void testSave() {
        Factura f = new Factura(4L, "44.444.444-4", LocalDateTime.now(), new BigDecimal("15000"), new BigDecimal("2850"), new BigDecimal("17850"));

        when(facturaRepository.save(f)).thenReturn(f);

        Factura resultado = facturaService.save(f);

        assertEquals("44.444.444-4", resultado.getRutCliente());
    }

    @Test
    public void testDeleteById() {
        Long id = 5L;
        facturaService.deleteById(id);

        verify(facturaRepository, times(1)).deleteById(id);
    }
}
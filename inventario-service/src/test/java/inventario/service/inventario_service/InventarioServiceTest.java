package inventario.service.inventario_service;

import inventario.service.inventario_service.entidades.Inventario;
import inventario.service.inventario_service.repositorio.InventarioRepository;
import inventario.service.inventario_service.servicio.InventarioService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        Inventario i1 = new Inventario(1, 1001, 2001, 15);
        Inventario i2 = new Inventario(2, 1002, 2002, 30);

        when(inventarioRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

        List<Inventario> resultado = inventarioService.getAll();

        assertEquals(2, resultado.size());
        assertEquals(1002, resultado.get(1).getProductoId());
    }

    @Test
    public void testGetById_Existe() {
        Inventario i = new Inventario(1, 1001, 2001, 50);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(i));

        Inventario resultado = inventarioService.getById(1L);

        assertNotNull(resultado);
        assertEquals(1001, resultado.getProductoId());
    }

    @Test
    public void testGetById_NoExiste() {
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        Inventario resultado = inventarioService.getById(99L);

        assertNull(resultado);
    }

    @Test
    public void testSave() {
        Inventario nuevo = new Inventario(0, 5555, 9999, 20);
        when(inventarioRepository.save(nuevo)).thenReturn(nuevo);

        Inventario resultado = inventarioService.save(nuevo);

        assertEquals(5555, resultado.getProductoId());
    }

    @Test
    public void testDeleteById() {
        Long id = 10L;

        inventarioService.deleteById(id);

        verify(inventarioRepository, times(1)).deleteById(id);
    }
}

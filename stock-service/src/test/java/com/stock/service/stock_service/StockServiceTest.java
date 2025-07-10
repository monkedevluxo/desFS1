package com.stock.service.stock_service;

import com.stock.service.stock_service.servicio.StockService;
import com.stock.service.stock_service.entidades.Stock;
import com.stock.service.stock_service.repositorio.StockRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    @Test
    public void testGetAllStocks() {
        Stock s1 = new Stock(1, 10, 100);
        Stock s2 = new Stock(2, 20, 200);
        when(stockRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Stock> stocks = stockService.getAll();

        assertEquals(2, stocks.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void testGetStockByIdExists() {
        Stock s = new Stock(1, 10, 100);
        when(stockRepository.findById(1)).thenReturn(Optional.of(s));

        Stock result = stockService.getStockById(1);
        assertNotNull(result);
        assertEquals(10, result.getCantidad());
    }

    @Test
    public void testGetStockByIdNotExists() {
        when(stockRepository.findById(99)).thenReturn(Optional.empty());

        Stock result = stockService.getStockById(99);
        assertNull(result);
    }

    @Test
    public void testSaveStock() {
        Stock s = new Stock(3, 15, 300);
        when(stockRepository.save(s)).thenReturn(s);

        Stock result = stockService.save(s);
        assertEquals(15, result.getCantidad());
    }

    @Test
    public void testDeleteStock() {
        doNothing().when(stockRepository).deleteById(1);
        stockService.deleteStockById(1);
        verify(stockRepository, times(1)).deleteById(1);
    }

    @Test
    public void testByProductoId() {
        Stock s = new Stock(1, 25, 123);
        when(stockRepository.findByProductoId(123)).thenReturn(Arrays.asList(s));

        List<Stock> result = stockService.byProductoId(123);
        assertEquals(1, result.size());
        assertEquals(25, result.get(0).getCantidad());
    }
}

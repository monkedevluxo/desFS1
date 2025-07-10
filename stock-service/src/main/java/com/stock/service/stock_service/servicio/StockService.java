package com.stock.service.stock_service.servicio;

import com.stock.service.stock_service.entidades.Stock;
import com.stock.service.stock_service.repositorio.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    public Stock getStockById(int id) {
        return stockRepository.findById(id).orElse(null);
    }

    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStockById(int id) {
        stockRepository.deleteById(id);
    }

    public List<Stock> byProductoId(int productoId) {
        return stockRepository.findByProductoId(productoId);
    }

    public Stock getByProductoId(int productoId) {
        List<Stock> stocks = stockRepository.findByProductoId(productoId);
        return stocks.isEmpty() ? null : stocks.get(0);
    }

    public void deleteById(int id) {
        stockRepository.deleteById(id);
    }
}
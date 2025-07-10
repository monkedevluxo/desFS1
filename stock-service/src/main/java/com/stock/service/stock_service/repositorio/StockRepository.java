package com.stock.service.stock_service.repositorio;


import com.stock.service.stock_service.entidades.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    // Método para encontrar un stock por productoId
    List<Stock> findByProductoId(int productoId);
}

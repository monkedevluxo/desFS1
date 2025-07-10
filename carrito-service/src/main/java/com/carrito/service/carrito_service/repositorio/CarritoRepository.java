package com.carrito.service.carrito_service.repositorio;

import com.carrito.service.carrito_service.entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    List<Carrito> findByUsuarioId(int usuarioId);
    
}

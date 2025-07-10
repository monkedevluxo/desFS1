package com.carrito.service.carrito_service.repositorio;

import com.carrito.service.carrito_service.entidades.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {
    Optional<ItemCarrito> findById(int id);
    List<ItemCarrito> findByCarritoId(int carritoId);
    void deleteById(int id);

}

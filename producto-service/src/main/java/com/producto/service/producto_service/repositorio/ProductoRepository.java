package com.producto.service.producto_service.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.producto.service.producto_service.entidades.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Integer>{
    List<Producto> findByUsuarioId(int usuarioId);

}

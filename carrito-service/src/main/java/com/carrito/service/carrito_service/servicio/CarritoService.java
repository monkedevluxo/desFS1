package com.carrito.service.carrito_service.servicio;

import com.carrito.service.carrito_service.entidades.Carrito;
import com.carrito.service.carrito_service.repositorio.CarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public List<Carrito> getAll() {
        return carritoRepository.findAll();
    }

    public Carrito getCarritoById(int id) {
        return carritoRepository.findById(id).orElse(null);
    }

    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void deleteCarritoById(int id) {
        carritoRepository.deleteById(id);
    }

    public List<Carrito> byUsuarioId(int usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId);
    }
}

package com.carrito.service.carrito_service.servicio;

import com.carrito.service.carrito_service.entidades.ItemCarrito;
import com.carrito.service.carrito_service.repositorio.ItemCarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCarritoService {

    private final ItemCarritoRepository itemCarritoRepository;

    public ItemCarritoService(ItemCarritoRepository itemCarritoRepository) {
        this.itemCarritoRepository = itemCarritoRepository;
    }

    public List<ItemCarrito> getAll() {
        return itemCarritoRepository.findAll();
    }

    public ItemCarrito getItemById(int id) {
        return itemCarritoRepository.findById(id).orElse(null);
    }

    public ItemCarrito save(ItemCarrito item) {
        return itemCarritoRepository.save(item);
    }

    public void deleteItemById(int id) {
        itemCarritoRepository.deleteById(id);
    }

    public List<ItemCarrito> byCarritoId(int carritoId) {
        return itemCarritoRepository.findByCarritoId(carritoId);
    }
}

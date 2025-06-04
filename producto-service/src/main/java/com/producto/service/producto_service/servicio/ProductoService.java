package com.producto.service.producto_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.producto.service.producto_service.entidades.Producto;
import com.producto.service.producto_service.repositorio.ProductoRepository;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAll(){
        return productoRepository.findAll();
    }

    public Producto getProductoById(int id){
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto producto){
        Producto nuevoProducto = productoRepository.save(producto);
        return nuevoProducto;
    }

    public List<Producto> byUsuarioId(int usuarioId){
        return productoRepository.findByUsuarioId(usuarioId);
    }

    public void deleteProductoById(int id) {
    productoRepository.deleteById(id);
}

}

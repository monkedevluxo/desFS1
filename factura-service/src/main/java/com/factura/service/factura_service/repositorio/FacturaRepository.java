package com.factura.service.factura_service.repositorio;

import com.factura.service.factura_service.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    // Método adicional para obtener facturas por RUT del cliente
    List<Factura> findByRutCliente(String rutCliente);

    // Método adicional por si quieres buscar por boleta
    Factura findByBoletaId(Long boletaId);
}
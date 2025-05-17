package com.factura.service.factura_service.servicio;
import com.factura.service.factura_service.entidades.Factura;
import com.factura.service.factura_service.repositorio.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    // Obtener todas las facturas
    public List<Factura> getAll() {
        return facturaRepository.findAll();
    }

    // Obtener factura por ID
    public Factura getById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    // Obtener facturas por RUT
    public List<Factura> getByRutCliente(String rut) {
        return facturaRepository.findByRutCliente(rut);
    }

    // Obtener factura por boleta
    public Factura getByBoletaId(Long boletaId) {
        return facturaRepository.findByBoletaId(boletaId);
    }

    // Guardar factura (calcula IVA y totales automáticamente)
    public Factura save(Factura factura) {
        factura.setFecha(LocalDateTime.now());

        BigDecimal totalNeto = factura.getTotalNeto();

        BigDecimal iva = totalNeto.multiply(BigDecimal.valueOf(0.19)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFinal = totalNeto.add(iva);

        factura.setIva(iva);
        factura.setTotalFinal(totalFinal);

        return facturaRepository.save(factura);
    }

    // Eliminar factura
    public void deleteById(Long id) {
        facturaRepository.deleteById(id);
    }
}